package com.hawa.dto.job;

import lombok.Data;

@Data
public class PickupDto {
    private String address;
    private String comment;
    private ContactDto contact;

}
