package dev.aziz.services;

import dev.aziz.dtos.TaskDto;
import dev.aziz.entities.Comment;
import dev.aziz.entities.Task;
import dev.aziz.exceptions.AppException;
import dev.aziz.mappers.TaskMapper;
import dev.aziz.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = new TaskMapper();

    public List<TaskDto> getAllTasks() {
        return taskMapper.tasksToTaskDtos(taskRepository.findAll());
    }

    public Page<Task> getTasksByPageRequest(Long id, PageRequest pageRequest) {
        return taskRepository.findAllByPageRequest(id, pageRequest);
    }

    public TaskDto getOneTask(Long id) {
        return taskMapper.taskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND)));
    }

    public TaskDto createTask(Task task) {
        return taskMapper.taskToTaskDto(taskRepository.save(task));
    }

    public TaskDto deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND));
        taskRepository.deleteById(id);
        return taskMapper.taskToTaskDto(task);
    }

    public TaskDto editFullTask(Long id, Task task) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND));
        foundTask.setTitle(task.getTitle());
        foundTask.setStatus(task.getStatus());
        foundTask.setPriority(task.getPriority());
        foundTask.setComments(task.getComments());
        return taskMapper.taskToTaskDto(taskRepository.save(foundTask));
    }

    public TaskDto editTask(Long id, Task task) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND));

        if (task.getTitle() != null) {
            foundTask.setTitle(task.getTitle());
        }

        if (task.getStatus() != null) {
            foundTask.setStatus(task.getStatus());
        }

        if (task.getPriority() != null) {
            foundTask.setPriority(task.getPriority());
        }

        if (task.getComments() != null) {
            List<Comment> newComments = new ArrayList<>();
            for (Comment comment : task.getComments()) {
                if ("".equals(comment.getBody())) {
                    throw new AppException("Comment must not be empty.", HttpStatus.BAD_REQUEST);
                } else {
                    newComments.add(comment);
                }
            }
            foundTask.setComments(newComments);
        }
        return taskMapper.taskToTaskDto(taskRepository.save(foundTask));
    }

    public TaskDto addCommentToTask(Long id, Comment commentBody) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND));
        foundTask.getComments().add(commentBody);
        Task savedTask = taskRepository.save(foundTask);
        return taskMapper.taskToTaskDto(savedTask);
    }

    public TaskDto removeCommentFromTask(Long id, Long commentId) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND));
        foundTask.getComments().removeIf(comment -> comment.getId().equals(commentId));
        Task savedTask = taskRepository.save(foundTask);
        return taskMapper.taskToTaskDto(savedTask);
    }

    public TaskDto editCommentFromTask(Long id, Comment comment) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new AppException("Task not found.", HttpStatus.NOT_FOUND));
        Comment foundComment = foundTask.getComments().stream()
                .filter(c -> c.getId().equals(comment.getId()))
                .findFirst()
                .orElseThrow(() -> new AppException("Comment not found with id: " + comment.getId(), HttpStatus.NOT_FOUND));
        foundComment.setBody(comment.getBody());
        Task savedTask = taskRepository.save(foundTask);
        return taskMapper.taskToTaskDto(savedTask);
    }

}
