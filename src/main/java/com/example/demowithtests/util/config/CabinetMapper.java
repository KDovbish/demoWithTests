package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.CabinetCreateDto;
import com.example.demowithtests.dto.CabinetResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CabinetMapper {
    //  Отображение использующиеся только при создании сущности Кабинет
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "capacity", defaultValue = "5")
    Cabinet cabinetCreateDtoToCabinet(CabinetCreateDto cabinetCreateDto);

    CabinetResponseDto cabinetToCabinetResponseDto(Cabinet cabinet);
}
