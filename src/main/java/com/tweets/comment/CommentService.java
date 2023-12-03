package com.tweets.comment;

import com.tweets.common.exception.ResourceNotFoundException;
import com.tweets.comment.dto.CommentDto;
import com.tweets.comment.entity.Comment;
import com.tweets.comment.mapper.CommentMapper;
import com.tweets.common.exception.TweetsAPIException;
import com.tweets.post.PostRepository;
import com.tweets.post.entity.Post;
import com.tweets.user.UserRepository;
import com.tweets.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = CommentMapper.mapToComment(commentDto);

        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() ->
                        new TweetsAPIException(HttpStatus.NOT_FOUND,"User with id: " + commentDto.getUserId() + " does not exist"));

        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() ->
                        new TweetsAPIException(HttpStatus.NOT_FOUND, "Post with id: " + commentDto.getPostId() + " does not exist"));

        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"Comment with the given id: " + commentId + " does not exist")
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
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"Comment with the given id: " + commentId + " does not exist")
        );

        comment.setContent(updatedComment.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND, "Comment with the given id: " + commentId + " does not exist")
        );

        commentRepository.deleteById(commentId);
    }
}
