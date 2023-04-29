package com.sparta.spring_post.repository;

import com.sparta.spring_post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
