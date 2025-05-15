package com.test.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "p_user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public static UserEntity createUser(String username, String password, String nickname) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .role(UserRole.USER)
                .build();
    }
}
