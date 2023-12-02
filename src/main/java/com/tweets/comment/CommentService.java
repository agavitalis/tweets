package com.tweets.comment;

import com.tweets.common.exception.ResourceNotFoundException;
import com.tweets.comment.dto.CommentDto;
import com.tweets.comment.entity.Comment;
import com.tweets.comment.mapper.CommentMapper;
import com.tweets.user.UserRepository;
import com.tweets.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = CommentMapper.mapToComment(commentDto);

        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id: " + commentDto.getUserId() + " does not exist"));
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment with the given id: " + commentId + " does not exist")
        );
        return CommentMapper.mapToCommentDto(comment);
    }


    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long commentId, CommentDto updatedComment) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment does not exists with a given id:"+ commentId)
        );

        comment.setContent(updatedComment.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment is not exists with a given id: " + commentId)
        );

        commentRepository.deleteById(commentId);
    }
}
