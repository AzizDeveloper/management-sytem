package dev.aziz.mappers;

import dev.aziz.dtos.TaskDto;
import dev.aziz.entities.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    private final CommentMapper commentMapper = new CommentMapper();

    public TaskDto taskToTaskDto(Task task) {
        if (task == null) {
            return null;
        }
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .priority(task.getPriority())
                .comments(commentMapper.commentsToCommentDtos(task.getComments()))
                .build();
    }

    public Task taskDtoToTask(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }
        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .comments(commentMapper.commentDtosToComments(taskDto.getComments()))
                .build();
    }

    public List<TaskDto> tasksToTaskDtos(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }
        return tasks.stream().map(this::taskToTaskDto).collect(Collectors.toList());
    }

    public List<Task> taskDtosToTasks(List<TaskDto> taskDtos) {
        if (taskDtos == null) {
            return null;
        }
        return taskDtos.stream().map(this::taskDtoToTask).collect(Collectors.toList());
    }

}
