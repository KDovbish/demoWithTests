package com.example.demowithtests.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
public class PhotoDto {
    public Integer id;
    public Date addDate;
    public String description;
    public String cameraType;
    public String photoUrl;
}
