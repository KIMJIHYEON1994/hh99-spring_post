package com.sparta.spring_post.repository;

import com.sparta.spring_post.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

}
