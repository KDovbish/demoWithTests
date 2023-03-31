package com.example.demowithtests.web;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Passport", description = "Passport API")
public interface PassportController {
    /**
     * Генерировать заданное количество сущностей Паспорт в бд
     * @param n Требуемое количество
     */
    @Operation(summary = "Создать Паспорт(а)", description = "Генерировать заданное количество сущностей Паспорт", tags = {"Passport"})
    void generate(Integer n);

    /**
     * Получить пул всех свободных(не привязанных к сущностям Сотрудник) Паспортов
     * @return Список сущностей Паспорт
     */
    @Operation(summary = "Запросить все свободные паспорта", description = "Выбрать все сущности Паспорт, у которых нет связи на Сотрудника", tags = {"Passport"})
    List<PassportResponseDto> getAllFree();


    /**
     * Получить сущность Паспорт
     * @param passportId Идентификатор сущности Паспорт в бд
     * @return сущность Паспорт
     */
    @Operation(summary = "Запросить Паспорт", tags = {"Passport"})
    PassportResponseDto getPassportById(Integer passportId);


    /**
     * Логическое удаление сущности Паспорт
     * @param passportId Идентификатор Паспорта в бд
     */
    @Operation(summary = "Удалить Паспорт", description = "Логическое удаление Паспорта с разрывом связи(если существует) от Сотрудника", tags = {"Passport"})
    void removeById(Integer passportId);



/*
     * Обновить содержимое заданной сущности Паспорт
     * @param passportId Идентификатор Паспорта в бд
     * @param passportRequestDto Параметры для обновления
     * @return Обновленная сущность Паспорт
    @Operation(summary = "Обновить Паспорт", description = "Обновить существующий Паспорт по идентификатору в бд", tags = {"Passport"})
    @Parameters(value = {
            @Parameter(name = "passportId", description = "Идентификатор Паспорта в бд"),
            @Parameter(name = "passportRequestDto", description = "Параметры паспорта для обновления")
    })
    PassportResponseDto update(Integer passportId, PassportRequestDto passportRequestDto);
*/

}
