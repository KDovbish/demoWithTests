package com.example.demowithtests.web;

import com.example.demowithtests.dto.CabinetCreateDto;
import com.example.demowithtests.dto.CabinetResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Cabinet", description = "Cabinet API")
public interface CabinetController {
    @Operation(summary = "Создать Кабинет", tags = {"Cabinet"})
    CabinetResponseDto createCabinet(CabinetCreateDto cabinetCreateDto);

    @Operation(summary = "Получить Кабинет", description = "Получить сущность Кабинет по идентификатору в бд", tags = {"Cabinet"})
    CabinetResponseDto readCabinet(@PathVariable Integer id);

    @Operation(summary = "Получить все Кабинеты", description = "Получить все неудаленные логически сущности Кабинет", tags = {"Cabinet"})
    List<CabinetResponseDto> readAllCabinet();

    @Operation(summary = "Удалить Кабинет", description = "Логическое(простановка метки) удаление Кабинета", tags = {"Cabinet"})
    void deleteCabinet(Integer id);
}
