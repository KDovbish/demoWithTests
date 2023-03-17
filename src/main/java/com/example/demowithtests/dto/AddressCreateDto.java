package com.example.demowithtests.dto;

import lombok.ToString;

@ToString
public class AddressCreateDto {
    public Boolean addressHasActive;
    public String country;
    public String city;
    public String street;
}
