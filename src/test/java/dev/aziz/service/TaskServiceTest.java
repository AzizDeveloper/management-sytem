package dev.aziz.service;

import dev.aziz.dtos.TaskDto;
import dev.aziz.entities.Priority;
import dev.aziz.entities.Status;
import dev.aziz.entities.Task;
import dev.aziz.entities.Comment;
import dev.aziz.mappers.TaskMapper;
import dev.aziz.repositories.TaskRepository;
import dev.aziz.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@RequiredArgsConstructor
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void getTaskByIdTest() {
        //given
        Task task = Task.builder()
                .id(1L)
                .status(Status.DONE)
                .title("task title")
                .priority(Priority.HIGH)
                .build();

        //when
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //then
        TaskDto result = taskService.getTaskById(1L);

        assertAll(() -> {
            assertEquals(task.getId(), result.getId());
            assertEquals(task.getStatus(), result.getStatus());
            assertEquals(task.getTitle(), result.getTitle());
            assertEquals(task.getPriority(), result.getPriority());
        });
    }

    @Test
    void createTaskTest() {
        //given
        Task task = Task.builder()
//                .id(1L)
                .status(Status.DONE)
                .title("task title")
                .priority(Priority.HIGH)
                .build();
        Task savedTask = Task.builder()
                .id(1L)
                .status(Status.DONE)
                .title("task title")
                .priority(Priority.HIGH)
                .build();


        //when
        when(taskRepository.save(task)).thenReturn(savedTask);
        //then
        TaskDto result = taskService.createTask(task);

        assertAll(() -> {
            assertEquals(task.getStatus(), result.getStatus());
            assertEquals(task.getTitle(), result.getTitle());
            assertEquals(task.getPriority(), result.getPriority());
        });
    }

    @Test
    void addCommentToTask_shouldAddCommentAndReturnUpdatedTaskDto() {
        // Given
        Long taskId = 1L;
        Task task = Task.builder()
                .id(taskId)
                .comments(new ArrayList<>())
                .build();

        Comment comment = Comment.builder()
                .id(100L)
                .body("New Comment")
                .build();

        Task updatedTask = Task.builder()
                .id(taskId)
                .comments(List.of(comment))
                .build();

        TaskDto expectedTaskDto = TaskDto.builder()
                .id(taskId)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // When
        TaskDto result = taskService.addCommentToTask(taskId, comment);
        System.out.println(result);
        // Then
        assertAll(() -> {
            assertNotNull(result);
            assertNotNull(result.getComments());
            assertEquals(task.getId(), result.getId());
        });

        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(any(Task.class));
    }

}
