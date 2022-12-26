package com.restapi.springbootrestapi.service;

import com.restapi.springbootrestapi.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long post_Id, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long post_ID, Long commentID);

    CommentDto updateComment(Long post_ID, Long commentID, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);
}
