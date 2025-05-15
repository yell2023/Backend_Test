package com.test.user.service;

import com.test.user.common.exception.BadRequestException;
import com.test.user.common.jwt.JwtUtil;
import com.test.user.dto.request.SigninRequestDto;
import com.test.user.dto.response.SigninResponseDto;
import com.test.user.entity.UserEntity;
import com.test.user.entity.UserRole;
import com.test.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인 성공")
    void signin_success() {
        // given
        SigninRequestDto requestDto = new SigninRequestDto("testuser", "password123");

        UserEntity mockUser = UserEntity.builder()
                .username("testuser")
                .password("encodedPassword")
                .role(UserRole.USER)
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtUtil.createToken("testuser", UserRole.USER)).thenReturn("test.jwt.token");

        // when
        SigninResponseDto response = authService.signin(requestDto);

        // then
        assertEquals("test.jwt.token", response.getToken());
    }

    @Test
    @DisplayName("로그인 실패_존재하지 않는 아이디")
    void signin_fail_username() {
        // given
        SigninRequestDto requestDto = new SigninRequestDto("wronguser", "password");

        when(userRepository.findByUsername("wronguser")).thenReturn(Optional.empty());

        // when & then
        assertThrows(BadRequestException.class, () -> authService.signin(requestDto));
    }

    @Test
    @DisplayName("로그인 실패_비밀번호 불일치")
    void signin_fail_password() {
        // given
        SigninRequestDto requestDto = new SigninRequestDto("testuser", "wrongpassword");

        UserEntity mockUser = UserEntity.builder()
                .username("testuser")
                .password("encodedPassword")
                .role(UserRole.USER)
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        // when & then
        assertThrows(BadRequestException.class, () -> authService.signin(requestDto));
    }
}
