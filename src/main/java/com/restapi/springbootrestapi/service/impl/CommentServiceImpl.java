package com.restapi.springbootrestapi.service.impl;

import com.restapi.springbootrestapi.dtos.CommentDto;
import com.restapi.springbootrestapi.entity.Comment;
import com.restapi.springbootrestapi.entity.Post;
import com.restapi.springbootrestapi.exception.BlogAPIException;
import com.restapi.springbootrestapi.exception.ResourceNotFoundException;
import com.restapi.springbootrestapi.repository.CommentRepository;
import com.restapi.springbootrestapi.repository.PostRepository;
import com.restapi.springbootrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long post_Id, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        Post post = getPostFromRepo(post_Id);
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
        Post post = getPostFromRepo(post_Id);
        Comment comment = getCommentFromRepo(commentId);
        checkForAPIException(post,comment);

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long post_Id, Long commentId, CommentDto commentDto) {

        Post post = getPostFromRepo(post_Id);
        Comment comment = getCommentFromRepo(commentId);
        checkForAPIException(post,comment);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = getPostFromRepo(postId);
        Comment comment = getCommentFromRepo(commentId);
        checkForAPIException(post, comment);
        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    private Post getPostFromRepo(Long postId){
        return postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment getCommentFromRepo(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));
    }

    private void checkForAPIException(Post post, Comment comment){
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }
}
