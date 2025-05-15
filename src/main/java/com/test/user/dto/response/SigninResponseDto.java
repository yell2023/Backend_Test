package com.test.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninResponseDto {

    private String token;

    public static SigninResponseDto from(String token) {
        return new SigninResponseDto(token);
    }
}
