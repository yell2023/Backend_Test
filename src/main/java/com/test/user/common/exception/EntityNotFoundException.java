package com.test.user.common.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends UserException {
    public EntityNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_ERROR, message);
    }
}
