package com.tweets.post;

import com.tweets.common.exception.TweetsAPIException;
import com.tweets.common.response.PaginatedResponse;
import com.tweets.post.dto.PostDto;
import com.tweets.post.entity.Like;
import com.tweets.post.entity.Post;
import com.tweets.post.mapper.PostMapper;
import com.tweets.user.UserRepository;
import com.tweets.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService implements IPostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private LikeRepository likeRepository;

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
    public PaginatedResponse getAllPosts(int pageNo, int pageSize, String email) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> postList;
        List<Post> listOfPost;
        if(email != null){
            postList = postRepository.findByUserEmail(email,pageable );
            listOfPost = postList.getContent();
        }else{
            postList = postRepository.findAll(pageable);
            listOfPost = postList.getContent();
        }

        List<PostDto> listOfPostDto = listOfPost.stream().map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());

        PaginatedResponse pagedResponse = new PaginatedResponse();
        pagedResponse.setData(listOfPostDto);
        pagedResponse.setMessage("Posts Successfully Retrieved");
        pagedResponse.setStatus(HttpStatus.OK.toString());
        pagedResponse.setPageNo(postList.getNumber());
        pagedResponse.setPageSize(postList.getSize());
        pagedResponse.setTotalElements(postList.getTotalElements());
        pagedResponse.setTotalPages(postList.getTotalPages());
        pagedResponse.setLast(postList.isLast());
        return pagedResponse;
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

    @Override
    @Transactional
    public void likePost(Long userId, Long postId) {
        // Check if the user has already liked the post
        if (!hasUserLikedPost(userId, postId)) {
            // If not, proceed to like the post
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new TweetsAPIException(HttpStatus.NOT_FOUND,"User not found with id: " + userId));

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new TweetsAPIException(HttpStatus.NOT_FOUND,"Post not found with id: " + postId));

            Like like = new Like();
            like.setUser(user);
            like.setPost(post);

            likeRepository.save(like);

            post.setNumberOfLikes(post.getNumberOfLikes() == null ? 1 : post.getNumberOfLikes() + 1);
            postRepository.save(post);
        } else {
            // User has already liked the post, handle accordingly (throw exception, return a message, etc.)
            throw new TweetsAPIException(HttpStatus.CONFLICT, "User with id " + userId + " has already liked the post with id " + postId);
        }
    }

    private boolean hasUserLikedPost(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
