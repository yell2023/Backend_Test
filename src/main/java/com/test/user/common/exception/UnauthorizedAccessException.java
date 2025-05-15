package com.test.user.common.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends UserException {
    public UnauthorizedAccessException(String message) {
        super(ErrorCode.AUTHORITY_ERROR, message);
    }
}
