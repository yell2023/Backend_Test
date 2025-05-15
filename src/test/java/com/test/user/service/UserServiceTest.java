package com.test.user.service;

import com.test.user.common.exception.DuplicateResourceException;
import com.test.user.dto.request.SignupRequestDto;
import com.test.user.dto.response.UserResponseDto;
import com.test.user.entity.UserEntity;
import com.test.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // given
        SignupRequestDto requestDto = new SignupRequestDto("testuser", "password123", "닉네임");

        // username 중복 확인
        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.empty());

        // 비밀번호 암호화
        Mockito.when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        // 사용자 저장 시 리턴값 설정
        UserEntity user = UserEntity.createUser("testuser", "encodedPassword", "닉네임");
        ReflectionTestUtils.setField(user, "id", 1L); // 테스트용 id

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class)))
                .thenReturn(user);

        // when
        UserResponseDto response = userService.signup(requestDto);

        // then
        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals("닉네임", response.getNickname());
    }

    @Test
    @DisplayName("회원가입 실패_유저네임 중복")
    void signup_fail_duplicateUsername() {
        // given
        SignupRequestDto requestDto = new SignupRequestDto("testuser", "password123", "닉네임");

        // 이미 존재하는 유저
        UserEntity existingUser = UserEntity.createUser("testuser", "encodedPassword", "닉네임");

        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(existingUser));

        // when & then
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> {
            userService.signup(requestDto);
        });

        assertEquals("이미 존재하는 유저이름입니다.", exception.getMessage());
    }
}
