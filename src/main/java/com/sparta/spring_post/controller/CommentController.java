package com.sparta.spring_post.controller;

import com.sparta.spring_post.dto.CommentRequestDto;
import com.sparta.spring_post.dto.UserResponseDto;
import com.sparta.spring_post.entity.Comment;
import com.sparta.spring_post.security.UserDetailsImpl;
import com.sparta.spring_post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@Api(value = "CommentController", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comment")
//    @ApiOperation(value = "댓글 작성", notes = "댓글 작성 설명")
    public UserResponseDto<Comment> addComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.addComment(commentRequestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/comment/{id}")
//    @ApiOperation(value = "댓글 수정", notes = "댓글 수정 설명")
    public UserResponseDto<Comment> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
//    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 설명")
    public UserResponseDto<Comment> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }

    // 댓글 좋아요
    @PutMapping("/comment/like/{id}")
//    @ApiOperation(value = "댓글 좋아요", notes = "댓글 좋아요 설명")
    public UserResponseDto<Comment> likeComment(@PathVariable Long id) {
        return commentService.likeComment(id);
    }

}