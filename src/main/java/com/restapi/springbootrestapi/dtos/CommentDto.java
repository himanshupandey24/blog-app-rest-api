package com.restapi.springbootrestapi.dtos;

import lombok.Data;

@Data
public class CommentDto {

    private long id;
    private String name;
    private String email;
    private String body;
}
