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
}
