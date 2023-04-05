package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeCabinetJoinEntity;

public interface EmployeeCabinetJoinService {
    /**
     * Создать в join-таблице связь между Сотрудником и Кабинетом
     * @param employee Сущность Сотрудник
     * @param cabinet Сущность Кабинет
     * @return Созданная сущность связи
     */
    EmployeeCabinetJoinEntity makeJoin(Employee employee, Cabinet cabinet);

    /**
     * Получить сущность соединяющей таблицы Сотрудник-Кабинет
     * @param employeeId Идентификатор Сотрудника в бд
     * @param cabinetId Идентификатор Кабинета в бд
     * @return Сущность соединяющей таблицы
     */
    EmployeeCabinetJoinEntity getById(Integer employeeId, Integer cabinetId);

    /**
     * Прописать статус связи Сотрудник - Кабинет
     * @param employeeId Идентификатор Сотрудника в бд
     * @param cabinetId Идентификатор Кабинета в бд
     * @param status активная(true)/неактивная(false)
     * @return Обновленная сущность соединяющей таблицы
     */
    EmployeeCabinetJoinEntity setJoinStatus(Integer employeeId, Integer cabinetId, Boolean status);

    /**
     * Физически удалить связь Сотрудник - Кабинет
     * @param employeeId Идентификатор Сотрудника в бд
     * @param cabinetId Идентификатор Кабинета в бд
     */
    void removeJoin(Integer employeeId, Integer cabinetId);


}
