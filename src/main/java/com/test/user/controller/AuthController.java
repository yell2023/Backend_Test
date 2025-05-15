package com.test.user.controller;

import com.test.user.dto.request.SigninRequestDto;
import com.test.user.dto.response.SigninResponseDto;
import com.test.user.dto.response.ResDTO;
import com.test.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ResDTO<SigninResponseDto>> signin(@Valid @RequestBody SigninRequestDto requestDto) {

        return new ResponseEntity<>(
                ResDTO.<SigninResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .message("로그인에 성공했습니다.")
                        .data(authService.signin(requestDto))
                        .build(),
                HttpStatus.OK
        );
    }
}
