package com.example.demowithtests.web;

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
}
