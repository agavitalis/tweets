package com.tweets.comment;

import com.tweets.comment.dto.CommentDto;
import com.tweets.common.response.CustomResponse;
import com.tweets.common.response.PaginatedResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CustomResponse> createComment(@RequestBody CommentDto commentDto){
        CommentDto comment = commentService.createComment(commentDto);

        CustomResponse response = new CustomResponse(HttpStatus.CREATED.toString(), "Comment Successfully created", comment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getCommentById(@PathVariable("id") Long commentId){
        CommentDto commentDto = commentService.getCommentById(commentId);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Comment Successfully retrieved", commentDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse> getAllComments(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        PaginatedResponse comments = commentService.getAllComments(pageNo, pageSize);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updateComment(@PathVariable("id") Long commentId,
                                                          @RequestBody CommentDto updatedComment){
        CommentDto commentDto = commentService.updateComment(commentId, updatedComment);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Comment Successfully updated", commentDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse> deleteComment(@PathVariable("id") Long commentId){
        commentService.deleteComment(commentId);
        CustomResponse response = new CustomResponse(HttpStatus.OK.toString(), "Comment Successfully deleted", null);
        return ResponseEntity.ok(response);
    }

}
