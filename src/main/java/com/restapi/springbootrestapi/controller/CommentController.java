package com.restapi.springbootrestapi.controller;


import com.restapi.springbootrestapi.dtos.CommentDto;
import com.restapi.springbootrestapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("id") Long commentId){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId,
                                                    @PathVariable("id") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);

        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId,
                                                @PathVariable("id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }

}
