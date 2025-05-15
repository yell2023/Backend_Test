package com.test.user.controller;

import com.test.user.dto.request.SignupRequestDto;
import com.test.user.dto.response.ResDTO;
import com.test.user.dto.response.UserResponseDto;
import com.test.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResDTO<UserResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) {

        return new ResponseEntity<>(
                ResDTO.<UserResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("회원가입에 성공했습니다.")
                        .data(userService.signup(requestDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("admin/users/{username}/roles")
    public ResponseEntity<ResDTO<UserResponseDto>> patchUserRole(@PathVariable String username) {

        return new ResponseEntity<>(
                ResDTO.<UserResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .message("권한 변경에 성공했습니다.")
                        .data(userService.patchUserRole(username))
                        .build(),
                HttpStatus.OK
        );
    }
}
