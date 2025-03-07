package dev.aziz.controllers;

import dev.aziz.dtos.TaskDto;
import dev.aziz.entities.Comment;
import dev.aziz.entities.Task;
import dev.aziz.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Task management")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            description = "Get all tasks.",
            summary = "Get all tasks"
    )
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(
            description = "Get tasks by Page. First param is user id, second one is page and last one is page size",
            summary = "Get tasks by Page"
    )
    @GetMapping("/tasks/user")
    public ResponseEntity<Page<Task>> getAllTasksByPage(@RequestParam Long id, @RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.getTasksByPageRequest(id, pageRequest));
    }

    @Operation(
            description = "Get task by id. Id should be given in PathVariable",
            summary = "Get task by Id"
    )
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getOneTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(
            description = "Create endpoint for Task.",
            summary = "Create task post endpoint"
    )
    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody Task task) {
        return ResponseEntity.created(URI.create("/tasks/" + task.getId()))
                .body(taskService.createTask(task));
    }

    @Operation(
            description = "Delete endpoint for Task.",
            summary = "Delete task endpoint"
    )
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @Operation(
            description = "Put endpoint for Task. Task will be edited fully.",
            summary = "Edit task Put endpoint"
    )
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> editFullTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.editFullTask(id, task));
    }

    @Operation(
            description = "Patch endpoint for Task. Task can be edited by one field.",
            summary = "Edit task Patch endpoint"
    )
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> editTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.editTask(id, task));
    }

    @Operation(
            description = "Post endpoint for Task comments. Comment can be added to particular Task.",
            summary = "Add Comment to task Post endpoint"
    )
    @PostMapping("/tasks/{id}/comments")
    public ResponseEntity<TaskDto> addCommentToTask(@PathVariable Long id, @RequestBody Comment commentBody) {
        return ResponseEntity.ok(taskService.addCommentToTask(id, commentBody));
    }

    @Operation(
            description = "Delete endpoint for Task comments. Comment can be removed from particular Task.",
            summary = "Remove Comment from task Delete endpoint"
    )
    @DeleteMapping("/tasks/{id}/comments/{commentId}")
    public ResponseEntity<TaskDto> removeCommentFromTask(@PathVariable Long id, @PathVariable Long commentId) {
        return ResponseEntity.ok(taskService.removeCommentFromTask(id, commentId));
    }

    @Operation(
            description = "Delete endpoint for Task comments. Comment can be removed from particular Task.",
            summary = "Remove Comment from task Delete endpoint"
    )
    @PutMapping("/tasks/{id}/comments")
    public ResponseEntity<TaskDto> editCommentFromTask(@PathVariable Long id, @RequestBody Comment comment) {
        return ResponseEntity.ok(taskService.editCommentFromTask(id, comment));
    }

}
