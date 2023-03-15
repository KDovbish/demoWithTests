package com.example.demowithtests.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Date;

@ToString
public class PhotoDto {
    public Date addDate;
    public String description;
    public String cameraType;
    public String photoUrl;
}
