package com.restapi.springbootrestapi.service.impl;

import com.restapi.springbootrestapi.dtos.PostDto;
import com.restapi.springbootrestapi.entity.Post;
import com.restapi.springbootrestapi.exception.ResourceNotFoundException;
import com.restapi.springbootrestapi.repository.PostRepository;
import com.restapi.springbootrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = postRepository.save(mapToEntity(postDto));
        return mapToDTO(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .parallel()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost  = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
       postRepository.deleteById(id);
    }

    //Convert Entity to DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    //Convert DTO to Entity

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
