package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ContactDto {

    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    private String phone;
    private String email;
    private String company;
    @JsonProperty("company_name")
    private String companyName;
    public String getName(){
        return this.firstName.concat(" ").concat(this.lastName);
    }

}
