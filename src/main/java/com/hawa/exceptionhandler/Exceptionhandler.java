package com.hawa.exceptionhandler;


import com.hawa.dto.ErrorDto;
import com.hawa.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class Exceptionhandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleException(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    ApiException apiException) {
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiException.getErrorDto());
    }

}