package com.restapi.springbootrestapi.service;

import com.restapi.springbootrestapi.dtos.PostDto;
import com.restapi.springbootrestapi.dtos.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
