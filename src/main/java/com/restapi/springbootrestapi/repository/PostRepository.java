package com.restapi.springbootrestapi.repository;

import com.restapi.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
