package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;

import java.util.List;

public interface PassportService {
    /**
     * Создать заданное количество новых пустых Паспортов
     * @param n Требуемое количество
     */
    void generate(Integer n);

    /**
     * Получить все сущности Паспорт, которые не привязаны к сущностям Сотрудник
     * @return Список найденных сущностей Паспорт
     */
    List<Passport> getAllFree();
}
