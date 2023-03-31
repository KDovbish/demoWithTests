package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseChainDto;
import com.example.demowithtests.dto.PassportResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    PassportResponseDto passportToPassportResponseDto(Passport passport);
    Passport passportRequestDtoToPassport(PassportRequestDto passportRequestDto);

    @Mapping(target = "previd", source = "prev.id")
    PassportResponseChainDto passportToPassportResponseChainDto(Passport passport);
}
