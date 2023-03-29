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

    /**
     * Получить свободный Паспорт, т.е. не связанный ни с одной из сущностей Сотрудник
     * @return Сущность Паспорт
     */
    Passport getFree();

    /**
     * Получить сущность Паспорт по идентификатору в бд
     * @param id Идентификатор Паспорта в бд
     * @return Сущность Паспорт
     */
    Passport getById(Integer id);

    /**
     * Обновление сущности Паспорт
     * @param id Идентификатор Паспорта в бд
     * @param passport Параметры для обновления
     * @return Обновленная сущность Паспорт
     */
    Passport updateById(Integer id, Passport passport);

    /**
     * Логическое удаление сущности Паспорт
     * @param id Идентификатор Паспорта в бд
     */
    void removeById(Integer id);
}
