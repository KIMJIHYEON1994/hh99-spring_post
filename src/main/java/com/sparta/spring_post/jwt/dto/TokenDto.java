package com.sparta.spring_post.jwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
