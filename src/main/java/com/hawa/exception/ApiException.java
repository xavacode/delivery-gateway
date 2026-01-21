package com.hawa.exception;

import com.hawa.dto.ErrorDto;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private String message;
    private ErrorDto errorDto;

    public ApiException(String message, ErrorDto errorDto) {
        super(message);
        this.errorDto= errorDto;
    }
}
