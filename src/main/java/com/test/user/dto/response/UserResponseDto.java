package com.test.user.dto.response;

import com.test.user.entity.UserEntity;
import com.test.user.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@Schema(description = "유저 응답 Dto")
public class UserResponseDto {

    private String username;
    private String nickname;
    private UserRole role;

    public static UserResponseDto from(UserEntity user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
