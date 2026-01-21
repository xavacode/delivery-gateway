package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobPricingResponseDto {

    private BigDecimal amount;
    @JsonProperty("amount_with_tax")
    private BigDecimal amountWithTax;
    private String currency;
    @JsonProperty("dropoff_eta")
    private String dropoffEta;
}
