package com.tweets.post;

import com.tweets.post.dto.PostDto;

import java.util.List;

public interface IPostService {

    PostDto createPost(PostDto postDto);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPosts();

    PostDto updatePost(Long postId, PostDto updatedPost);

    void deletePost(Long postId);

    void likePost(Long userId, Long postId);
}
