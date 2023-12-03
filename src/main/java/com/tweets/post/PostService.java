package com.tweets.post;

import com.tweets.common.exception.ResourceNotFoundException;
import com.tweets.common.exception.TweetsAPIException;
import com.tweets.post.dto.PostDto;
import com.tweets.post.entity.Post;
import com.tweets.post.mapper.PostMapper;
import com.tweets.user.UserRepository;
import com.tweets.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService implements IPostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.mapToPost(postDto);

        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() ->
                        new TweetsAPIException(HttpStatus.NOT_FOUND,"User with id: " + postDto.getUserId() + " does not exist"));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return PostMapper.mapToPostDto(savedPost);
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"Post with the given id: " + postId + " does not exist")
        );
        return PostMapper.mapToPostDto(post);
    }


    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(Long postId, PostDto updatedPost) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"Post does not exists with a given id:"+ postId)
        );

        post.setContent(updatedPost.getContent());
        Post savedPost = postRepository.save(post);

        return PostMapper.mapToPostDto(savedPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.findById(postId).orElseThrow(
                () -> new TweetsAPIException(HttpStatus.NOT_FOUND,"Post is not exists with a given id: " + postId)
        );

        postRepository.deleteById(postId);
    }
}
