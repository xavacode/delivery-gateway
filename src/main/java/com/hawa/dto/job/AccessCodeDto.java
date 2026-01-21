package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccessCodeDto {

    private String code;
    private String description;
    private String type;
    private String title;
    private String instructions;
}
