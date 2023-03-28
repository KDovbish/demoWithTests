package com.example.demowithtests.service;

public interface PassportService {
    /**
     * Создать заданное количество новых пустых Паспортов
     * @param n Требуемое количество
     */
    void generate(Integer n);
}
