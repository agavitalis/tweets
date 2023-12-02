package com.tweets.post.mapper;

import com.tweets.post.dto.PostDto;
import com.tweets.post.entity.Post;

public class PostMapper {
    public static PostDto mapToPostDto(Post post){
        return new PostDto(
                post.getId(),
                post.getContent(),
                post.getNumberOfLikes(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getId()
        );
    }

    public static Post mapToPost(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setContent(postDto.getContent());
        return post;
    }
}
