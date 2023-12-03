package com.tweets.comment.mapper;

import com.tweets.comment.dto.CommentDto;
import com.tweets.comment.entity.Comment;

public class CommentMapper {
    public static CommentDto mapToCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser().getId(),
                comment.getPost().getId()
        );
    }

    public static Comment mapToComment(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        return comment;
    }
}
