package com.example.demowithtests.dto;

import com.example.demowithtests.domain.PassportState;

import java.time.LocalDateTime;

public class PassportResponseChainDto {
    public Integer id;
    public String firstName;
    public String secondName;
    public LocalDateTime dateOfBirthday;
    public String serialNumber;
    public LocalDateTime expireDate;
    public PassportState state;
    public Integer previd;
}
