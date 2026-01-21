package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricingDto {

    @JsonProperty("price_tax_included")
    private Double priceTaxIncluded;

    @JsonProperty("price_tax_excluded")
    private BigDecimal priceTaxExcluded;

    @JsonProperty("tax_amount")
    private BigDecimal taxAmount;

    @JsonProperty("invoice_url")
    private String invoiceUrl;

    @JsonProperty("tax_percentage")
    private BigDecimal taxPercentage;

    private String currency;

}
