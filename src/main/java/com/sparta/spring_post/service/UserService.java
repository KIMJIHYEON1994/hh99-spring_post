package com.sparta.spring_post.service;

import com.sparta.spring_post.dto.LoginRequestDto;
import com.sparta.spring_post.dto.UserResponseDto;
import com.sparta.spring_post.dto.SignupRequestDto;
import com.sparta.spring_post.entity.RoleType;
import com.sparta.spring_post.entity.Users;
import com.sparta.spring_post.jwt.JwtUtil;
import com.sparta.spring_post.repository.UserRepository;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    // UserRepository 연결
    private final UserRepository userRepository;
    // JwtUtil 연결
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public UserResponseDto<Users> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 아이디 형식 확인
        if (!Pattern.matches("^(?=.*[a-z])(?=.*\\d)[a-z0-9]{4,10}$", username)) {
            return UserResponseDto.setFailed("형식에 맞지 않는 아이디 입니다.");
        }

        // 비밀번호 형식 확인
        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&])[a-zA-Z\\d~!@#$%^&]{8,15}$", password)) {
            return UserResponseDto.setFailed("형식에 맞지 않는 비밀번호 입니다.");
        }

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return UserResponseDto.setFailed("중복된 사용자입니다.");
        }

        // 관리자 확인
        RoleType role = RoleType.USER;
        if (signupRequestDto.isAdmin()) {
            if (signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                return UserResponseDto.setFailed("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = RoleType.ADMIN;
        }

        Users users = new Users(username, password, role);
        userRepository.save(users);
        return UserResponseDto.setSuccess("회원가입 성공!");
    }

    @Transactional(readOnly = true)
    public UserResponseDto<Users> login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 아이디 확인
        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 아이디입니다.")
        );

        // 비밀번호 확인
        if (!users.getPassword().equals(password)) {
            return UserResponseDto.setFailed("일치하지 않는 비밀번호 입니다.");
        }

        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUsername(), users.getRole()));
        return UserResponseDto.setSuccess("로그인 성공!");
    }

}
