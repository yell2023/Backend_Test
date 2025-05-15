package com.test.user.dto.response;

import com.test.user.entity.UserEntity;
import com.test.user.entity.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
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
