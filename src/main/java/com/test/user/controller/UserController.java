package com.test.user.controller;

import com.test.user.dto.request.SignupRequestDto;
import com.test.user.dto.response.ResDTO;
import com.test.user.dto.response.UserResponseDto;
import com.test.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 API", description = "회원가입 및 사용자 권한 관리 API입니다.")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원가입", description = "새로운 사용자를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "회원가입 성공 예시",
                                    value = """
                    {
                      "code": 201,
                      "message": "회원가입에 성공했습니다.",
                      "data": {
                        "username": "jinho",
                        "nickname": "Mentos",
                        "role": "USER"
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효성 검사 실패 또는 중복 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "회원가입 실패 예시",
                                    value = """
                    {
                      "error": {
                        "code": "DUPLICATE_USER",
                        "message": "이미 존재하는 사용자입니다."
                      }
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<ResDTO<UserResponseDto>> signup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회원가입 요청 DTO",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SignupRequestDto.class),
                            examples = @ExampleObject(
                                    name = "회원가입 요청 예시",
                                    value = """
                    {
                      "username": "jinho",
                      "password": "password1234",
                      "nickname": "Mentos"
                    }
                    """
                            )
                    )
            )
            @Valid @RequestBody SignupRequestDto requestDto
    ) {
        return new ResponseEntity<>(
                ResDTO.<UserResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("회원가입에 성공했습니다.")
                        .data(userService.signup(requestDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "사용자 권한 변경 (ADMIN 전용)", description = "ADMIN 권한을 가진 사용자가 다른 사용자의 권한을 변경합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "권한 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "권한 변경 성공 예시",
                                    value = """
                    {
                      "code": 200,
                      "message": "권한 변경에 성공했습니다.",
                      "data": {
                        "username": "jinho",
                        "nickname": "Mentos",
                        "role": "ADMIN"
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "사용자 없음",
                                    value = """
                    {
                      "error": {
                        "code": 2003,
                        "message": "해당 유저를 찾을 수 없습니다."
                      }
                    }
                    """
                            )
                    )
            )
    })
    @Parameters({
            @Parameter(name = "username", description = "권한을 변경할 사용자 이름", required = true, example = "jinho")
    })
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
