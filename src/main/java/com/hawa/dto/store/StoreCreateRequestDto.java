package com.hawa.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StoreCreateRequestDto {

    private String title;
    private String name;
    private String phoneNumber;
    private String email;

}
