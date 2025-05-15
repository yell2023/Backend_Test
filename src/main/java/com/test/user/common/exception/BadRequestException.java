package com.test.user.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends UserException {
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST_ERROR, message);
    }
}
