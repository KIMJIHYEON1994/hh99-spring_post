package com.sparta.spring_post.controller;

import com.sparta.spring_post.dto.LoginRequestDto;
import com.sparta.spring_post.dto.QuitRequestDto;
import com.sparta.spring_post.dto.SignupRequestDto;
import com.sparta.spring_post.dto.UserResponseDto;
import com.sparta.spring_post.entity.Users;
import com.sparta.spring_post.security.UserDetailsImpl;
import com.sparta.spring_post.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Api(value = "UserController", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    @ApiOperation(value = "유저 회원가입", notes = "유저 회원가입 설명")
    public UserResponseDto<Users> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    // 로그인
    @PostMapping("/login")
    @ApiOperation(value = "유저 로그인", notes = "유저 로그인 설명")
    public UserResponseDto<Users> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return userService.login(loginRequestDto, httpServletResponse);
    }

    // 회원탈퇴
    @DeleteMapping("/quit")
    @ApiOperation(value = "유저 회원탈퇴", notes = "유저 회원탈퇴 설명")
    public UserResponseDto<Users> quit(@RequestBody QuitRequestDto quitRequestDto) {
        return userService.quit(quitRequestDto);
    }

}