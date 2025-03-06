package dev.aziz.services;

import dev.aziz.dtos.CommentDto;
import dev.aziz.entities.Comment;
import dev.aziz.mappers.CommentMapper;
import dev.aziz.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper = new CommentMapper();

    public CommentDto createComment(String body) {
        return commentMapper.commentToCommentDto(commentRepository.save(Comment.builder().body(body).build()));
    }

}
