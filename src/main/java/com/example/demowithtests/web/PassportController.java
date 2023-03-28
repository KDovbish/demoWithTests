package com.example.demowithtests.web;

public interface PassportController {
    /**
     * Генерировать заданное количество сущностей Паспорт в бд
     * @param n Требуемое количество
     */
    void generate(Integer n);
}
