package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.CabinetResponseDto;
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

    /**
     * Логически удалить сущность Кабинет.
     * Запись не удаляется из базы. Проставляется метка, указывающая что данную сущность следует считать удаленной.
     * @param id Идентификатор сущности Кабинет в бд
     */
    void removeCabinet(Integer id);

    /**
     * Обновление сущности Кабинет
     * @param id Идентификатор сущности Кабинет в бд
     * @param cabinet Параметры для обновления
     * @return Обновленная сущность Кабинет
     */
    Cabinet update(Integer id, Cabinet cabinet);


}
