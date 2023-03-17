package com.example.demowithtests.dto;

import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)
public class AddressDto {
    public Long id;
    public Boolean addressHasActive;
    public String country;
    public String city;
    public String street;

    public Date date = Date.from(Instant.now());
}
