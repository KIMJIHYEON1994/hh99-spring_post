package com.sparta.spring_post.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spring_post.dto.SecurityExceptionDto;
import com.sparta.spring_post.entity.Users;
import com.sparta.spring_post.jwt.util.JwtUtil;
import com.sparta.spring_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String access_token = jwtUtil.resolveToken(request, jwtUtil.ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request, jwtUtil.REFRESH_KEY);

        if (access_token != null) {
            if (jwtUtil.validateToken(access_token)) {
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token));
            } else if (refresh_token != null && jwtUtil.validateRefreshToken(refresh_token)) {
                String username = jwtUtil.getUserInfoFromToken(refresh_token);
                Users user = userRepository.findByUsername(username);
                String newAccessToken = jwtUtil.createToken(username, user.getRole(), "Access");
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                setAuthentication(username);
            } else if (refresh_token == null) {
                jwtExceptionHandler(response, "AccessToken Expired.", HttpStatus.BAD_REQUEST.value());
            } else {
                jwtExceptionHandler(response, "RefreshToken Expired.", HttpStatus.BAD_REQUEST.value());
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}