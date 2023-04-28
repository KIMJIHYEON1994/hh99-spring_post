package com.sparta.spring_post.controller;

import com.sparta.spring_post.dto.PostRequestDto;
import com.sparta.spring_post.dto.PostResponseDto;
import com.sparta.spring_post.dto.UserResponseDto;
import com.sparta.spring_post.entity.Post;
import com.sparta.spring_post.security.UserDetailsImpl;
import com.sparta.spring_post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
//@Api(value = "PostController", description = "게시글 관련 API")
public class PostController {

    // PostService 연결
    private final PostService postService;

    // 목록 조회
    @GetMapping("/posts")
//    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록 조회 설명")
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // 상세 조회
    @GetMapping("/posts/{id}")
//    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 상세 조회 설명")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 추가
    @PostMapping("/post")
//    @ApiOperation(value = "게시글 작성", notes = "게시글 작성 설명")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return null;
        }
        return postService.createPost(postRequestDto, userDetails.getUser());
    }

    // 수정
    @PutMapping("/post/{id}")
//    @ApiOperation(value = "게시글 수정", notes = "게시글 수정 설명")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    // 삭제
    @DeleteMapping("/post/{id}")
//    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제 설명")
    public UserResponseDto<Post> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }

    // 좋아요
    @PutMapping("/post/like/{id}")
//    @ApiOperation(value = "게시글 좋아요", notes = "게시글 좋아요 설명")
    public UserResponseDto<Post> updateLike(@PathVariable Long id) {
        return postService.updateLike(id);
    }

}