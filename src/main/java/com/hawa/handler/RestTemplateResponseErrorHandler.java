package com.hawa.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawa.dto.ErrorDto;
import com.hawa.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // Read error response
        String responseBody = StreamUtils.copyToString(
                response.getBody(),
                StandardCharsets.UTF_8
        );
        log.info("######response body: {}", responseBody);

        ObjectMapper mapper = new ObjectMapper();
        ErrorDto errorDto;

        try {
            errorDto = mapper.readValue(responseBody, ErrorDto.class);
        } catch (JsonProcessingException e) {
            // Fallback if error response doesn't match ErrorResponseDto
            errorDto = new ErrorDto("Unknown error", responseBody);
        }

//        throw new ApiException(
//                errorDto.getMessage(),
//                response.getStatusCode(),
//                errorDto
//        );

        throw new ApiException(errorDto.getMessage(), errorDto);

    }
}