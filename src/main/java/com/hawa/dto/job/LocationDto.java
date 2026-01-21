package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LocationDto {

    private Long id;
    private AddressDto address;
    private Double latitude;
    private Double longitude;
    private String comment;
    private ContactDto contact;

    @JsonProperty("access_codes")
    private List<AccessCodeDto> accessCodes;

    public String getAddress(){
        return this.address.getFormattedAddress();
    }

    public String getContactName(){
        return this.contact.getName();
    }

    public String getContactNumber(){
        return this.contact.getPhone();
    }


}
