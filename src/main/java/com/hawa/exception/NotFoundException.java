package com.hawa.exception;

import com.hawa.constant.ErrorCodeConstant;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final int errorCode;
    private final String errorDescription;

    public NotFoundException(String message) {
        super(message);
        this.errorCode= ErrorCodeConstant.NOT_FOUND.getCode();
        this.errorDescription= ErrorCodeConstant.NOT_FOUND.name();
    }
}
