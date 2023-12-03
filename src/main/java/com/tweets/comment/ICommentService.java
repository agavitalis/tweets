package com.tweets.comment;

import com.tweets.comment.dto.CommentDto;
import com.tweets.common.response.PaginatedResponse;

import java.util.List;

public interface ICommentService {

    CommentDto createComment(CommentDto commentDto);

    CommentDto getCommentById(Long commentId);

    PaginatedResponse getAllComments(int pageNo, int pageSize);

    CommentDto updateComment(Long commentId, CommentDto updatedComment);

    void deleteComment(Long commentId);
}
