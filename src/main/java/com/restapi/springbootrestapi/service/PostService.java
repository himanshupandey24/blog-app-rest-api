package com.restapi.springbootrestapi.service;

import com.restapi.springbootrestapi.dtos.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);

}
