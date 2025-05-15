package com.test.user.service;

import com.test.user.common.exception.DuplicateResourceException;
import com.test.user.common.exception.EntityNotFoundException;
import com.test.user.dto.request.SignupRequestDto;
import com.test.user.dto.response.UserResponseDto;
import com.test.user.entity.UserEntity;
import com.test.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signup(SignupRequestDto requestDto) {

        validateDuplicateUsername(requestDto.getUsername());

        UserEntity user = UserEntity.createUser(
                requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname()
        );

        return UserResponseDto.from(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto patchUserRole(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));

        user.modifyUserRole();

        return UserResponseDto.from(user);
    }

    private void validateDuplicateUsername(String username) {

        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new DuplicateResourceException("이미 존재하는 유저이름입니다.");
                });
    }
}
