package com.restapi.springbootrestapi.service.impl;

import com.restapi.springbootrestapi.dtos.CommentDto;
import com.restapi.springbootrestapi.entity.Comment;
import com.restapi.springbootrestapi.entity.Post;
import com.restapi.springbootrestapi.exception.BlogAPIException;
import com.restapi.springbootrestapi.exception.ResourceNotFoundException;
import com.restapi.springbootrestapi.repository.CommentRepository;
import com.restapi.springbootrestapi.repository.PostRepository;
import com.restapi.springbootrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(Long post_Id, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_Id));
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments
                .parallelStream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public CommentDto getCommentById(Long post_Id, Long commentId) {
        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_Id));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");


        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long post_Id, Long commentId, CommentDto commentDto) {

        Post post = postRepository.findById(post_Id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_Id));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}
