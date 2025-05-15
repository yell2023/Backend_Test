package com.test.user.controller;

import com.test.user.dto.request.SigninRequestDto;
import com.test.user.dto.response.SigninResponseDto;
import com.test.user.dto.response.ResDTO;
import com.test.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "JWT 기반 로그인 기능을 제공합니다.")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "로그인", description = "아이디와 비밀번호를 이용하여 로그인하고 JWT 토큰을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SigninResponseDto.class),
                            examples = @ExampleObject(
                                    name = "로그인 성공 예시",
                                    value = """
                    {
                      "code": 200,
                      "message": "로그인에 성공했습니다.",
                      "data": {
                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인 실패 - 잘못된 자격 증명",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "로그인 실패 예시",
                                    value = """
                    {
                      "error": {
                        "code": 2004,
                        "message": "존재하지 않는 아이디입니다."
                      }
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/signin")
    public ResponseEntity<ResDTO<SigninResponseDto>> signin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 요청 DTO",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SigninRequestDto.class),
                            examples = @ExampleObject(
                                    name = "로그인 요청 예시",
                                    value = """
                    {
                      "username": "user01",
                      "password": "password1234"
                    }
                    """
                            )
                    )
            )
            @Valid @RequestBody SigninRequestDto requestDto
    ) {
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
