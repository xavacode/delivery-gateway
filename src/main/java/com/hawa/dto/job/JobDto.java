package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JobDto {

    private List<PickupDto> pickups;
    private List<Dropoff> dropoffs;

}
