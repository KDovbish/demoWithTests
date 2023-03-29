package com.example.demowithtests.web;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;

import java.util.List;

public interface PassportController {
    /**
     * Генерировать заданное количество сущностей Паспорт в бд
     * @param n Требуемое количество
     */
    void generate(Integer n);

    /**
     * Получить пул всех свободных(не привязанных к сущностям Сотрудник) Паспортов
     * @return Список сущностей Паспорт
     */
    List<PassportResponseDto> getAllFree();


    /**
     * Получить сущность Паспорт
     * @param passportId Идентификатор сущности Паспорт в бд
     * @return сущность Паспорт
     */
    PassportResponseDto getPassportById(Integer passportId);

    /**
     * Обновить содержимое заданной сущности Паспорт
     * @param passportId Идентификатор Паспорта в бд
     * @param passportRequestDto Параметры для обновления
     * @return Обновленная сущность Паспорт
     */
    PassportResponseDto update(Integer passportId, PassportRequestDto passportRequestDto);

    /**
     * Логическое удаление сущности Паспорт
     * @param passportId Идентификатор Паспорта в бд
     */
    void removeById(Integer passportId);
}
