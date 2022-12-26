package com.restapi.springbootrestapi.controller;


import com.restapi.springbootrestapi.dtos.PostDto;
import com.restapi.springbootrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post rest api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts rest api
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    //update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable("id") long postId){
        return new ResponseEntity<>(postService.updatePost(postDto, postId), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deletePostById(@PathVariable("id") long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }
}
