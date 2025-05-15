package com.test.user.common.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends UserException {
    public DuplicateResourceException(String message) {
        super(ErrorCode.DUPLICATE_ERROR, message);
    }
}