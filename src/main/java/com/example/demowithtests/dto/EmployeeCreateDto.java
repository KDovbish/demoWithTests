package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
public class EmployeeCreateDto {
    public String name;
    public String country;
    public String email;
    public Set<AddressCreateDto> addresses = new HashSet<>();
    public Gender gender;
    public Set<PhotoCreateDto> photos = new HashSet<>();
}
