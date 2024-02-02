package com.tweets.post;

import com.tweets.common.response.CustomResponse;
import com.tweets.common.response.PaginatedResponse;
import com.tweets.post.PostService;
import com.tweets.post.dto.LikeDto;
import com.tweets.post.dto.PostDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    @PostMapping
    public ResponseEntity<CustomResponse> createPost(@RequestBody PostDto postDto){
        PostDto post = postService.createPost(postDto);

        CustomResponse response = new CustomResponse(HttpStatus.CREATED.toString(), "Post Successfully created", post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getPostById(@PathVariable("id") Long postId){
        PostDto postDto = postService.getPostById(postId);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Post Successfully retrieved", postDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam( value = "email", required = false ) String email
    ){
        PaginatedResponse posts = postService.getAllPosts(pageNo, pageSize, email);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updatePost(@PathVariable("id") Long postId,
                                                          @RequestBody PostDto updatedPost){
        PostDto postDto = postService.updatePost(postId, updatedPost);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Posts Successfully updated", postDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse> deletePost(@PathVariable("id") Long postId){
        postService.deletePost(postId);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Posts Successfully deleted", null);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/likePost")
    public ResponseEntity<CustomResponse> updatePost(@RequestBody LikeDto likeDto){
        postService.likePost(likeDto.getUserId(), likeDto.getPostId());
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Post Successfully liked", null);
        return ResponseEntity.ok(response);
    }

}
