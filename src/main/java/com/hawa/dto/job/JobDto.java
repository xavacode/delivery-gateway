package com.hawa.dto.job;

import lombok.Data;

import java.util.List;

@Data
public class JobDto {

    private List<PickupDto> pickups;
    private List<Dropoff> dropoffs;

}
