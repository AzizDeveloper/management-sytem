package dev.aziz.mappers;

import dev.aziz.dtos.CommentDto;
import dev.aziz.entities.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public CommentDto commentToCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .build();
    }

    public Comment commentDtoToComment(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }
        return Comment.builder()
                .id(commentDto.getId())
                .body(commentDto.getBody())
                .build();
    }

    public List<CommentDto> commentsToCommentDtos(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream().map(this::commentToCommentDto).collect(Collectors.toList());
    }

    public List<Comment> commentDtosToComments(List<CommentDto> commentDtos) {
        if (commentDtos == null) {
            return null;
        }
        return commentDtos.stream().map(this::commentDtoToComment).collect(Collectors.toList());
    }

}
