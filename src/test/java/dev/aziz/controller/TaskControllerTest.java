package dev.aziz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aziz.config.TestSecurityConfig;
import dev.aziz.controllers.TaskController;
import dev.aziz.dtos.TaskDto;
import dev.aziz.entities.Status;
import dev.aziz.entities.Task;
import dev.aziz.services.TaskService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("test")
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //    @MockBean
    @MockitoBean
    private TaskService taskService;

    @Test
    @SneakyThrows
//    @WithMockUser
    void getOneTaskTest() {
        TaskDto taskDto = TaskDto.builder()
                .id(2L)
                .title("Test Title")
                .status(Status.PROCESSING)
                .build();

        when(taskService.getTaskById(2L)).thenReturn(taskDto);

        mockMvc.perform(get("/tasks/{id}", 2L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.status").value(Status.PROCESSING.name()));
    }

    @Test
    @SneakyThrows
//    @WithMockUser
    void deleteTaskTest() {
        TaskDto taskDto = TaskDto.builder()
                .id(2L)
                .title("Test Title")
                .status(Status.PROCESSING)
                .build();

        when(taskService.deleteTask(2L)).thenReturn(taskDto);

        mockMvc.perform(delete("/tasks/{id}", 2L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.status").value(Status.PROCESSING.name()));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void createTaskTest() {
        Task task = Task.builder()
                .id(2L)
                .title("Test Title")
                .status(Status.PROCESSING)
                .build();

        String taskJSON = objectMapper.writeValueAsString(task);

        TaskDto taskDto = TaskDto.builder()
                .id(2L)
                .title("Test Title")
                .status(Status.PROCESSING)
                .build();

        when(taskService.createTask(task)).thenReturn(taskDto);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.status").value(Status.PROCESSING.name()));
    }

}
