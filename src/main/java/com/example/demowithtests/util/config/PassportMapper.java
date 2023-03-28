package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    PassportResponseDto passportToPassportResponseDto(Passport passport);
    Passport passportRequestDtoToPassport(PassportRequestDto passportRequestDto);
}
