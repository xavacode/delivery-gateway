package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JobValidationResponseDto {

    private Boolean valid;
}
