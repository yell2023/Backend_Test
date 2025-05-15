package com.test.user.common.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public UserException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
