package dev.aziz.dtos;

import dev.aziz.entities.Priority;
import dev.aziz.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    private Long id;

    private String title;

    private Status status;

    private Priority priority;

    private List<CommentDto> comments;

}
