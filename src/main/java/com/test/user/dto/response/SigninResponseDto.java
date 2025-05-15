package com.test.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 응답 Dto")
public class SigninResponseDto {

    private String token;

    public static SigninResponseDto from(String token) {
        return new SigninResponseDto(token);
    }
}
