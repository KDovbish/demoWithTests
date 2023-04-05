package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.Photo;

import java.util.HashSet;
import java.util.Set;

public class EmployeeResponseDto {
    public Integer id;
    public String name;
    public String country;
    public String email;
    public Set<AddressDto> addresses = new HashSet<>();
    public Gender gender;
    public Set<PhotoDto> photos = new HashSet<>();
    public PassportResponseDto passport;
    public Set<CabinetResponseDto> cabinets = new HashSet<>();
}
