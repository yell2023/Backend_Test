package com.test.user.service;

import com.test.user.common.exception.BadRequestException;
import com.test.user.common.jwt.JwtUtil;
import com.test.user.dto.request.SigninRequestDto;
import com.test.user.dto.response.SigninResponseDto;
import com.test.user.entity.UserEntity;
import com.test.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SigninResponseDto signin(SigninRequestDto requestDto) {

        UserEntity user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("비밀번호를 정확히 입력해주세요.");
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        return SigninResponseDto.from(token);
    }
}
