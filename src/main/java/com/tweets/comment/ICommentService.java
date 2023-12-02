package com.tweets.comment;

import com.tweets.comment.dto.CommentDto;

import java.util.List;

public interface ICommentService {

    CommentDto createComment(CommentDto commentDto);

    CommentDto getCommentById(Long commentId);

    List<CommentDto> getAllComments();

    CommentDto updateComment(Long commentId, CommentDto updatedComment);

    void deleteComment(Long commentId);
}
