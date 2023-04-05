package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CabinetService {
    /**
     * Добавить новую сущность Кабинет
     * @param cabinet Описание параметров добавляемой сущности
     * @return Новая сущость Кабинет
     */
    Cabinet addCabinet(Cabinet cabinet);

    /**
     * Получить сущность Кабинет
     * @param id Идентификатор сущности Кабинет в бд
     * @return Сущность Кабинет
     */
    Cabinet getCabinet(Integer id);

    /**
     * Получить список всех Кабинетов
     * @return Список сущностей Кабинет
     */
    List<Cabinet> getAll();


}
