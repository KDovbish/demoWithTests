package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeCabinetJoinEntity;
import com.example.demowithtests.domain.EmployeeCabinetJoinPrimaryKey;
import com.example.demowithtests.repository.EmployeeCabinetJoinRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeCabinetJoinServiceBean implements EmployeeCabinetJoinService{

    EmployeeCabinetJoinRepository employeeCabinetJoinRepository;

    //  Создать соединение Сотрудник - Кабинет
    @Override
    public EmployeeCabinetJoinEntity makeJoin(Employee employee, Cabinet cabinet) {
        EmployeeCabinetJoinEntity joinEntity = new EmployeeCabinetJoinEntity();
        joinEntity.setEmployee(employee);
        joinEntity.setCabinet(cabinet);
        joinEntity.setActive(Boolean.TRUE);
        return employeeCabinetJoinRepository.save(joinEntity);
    }

    //  Получить сущность соединения
    @Override
    public EmployeeCabinetJoinEntity getById(Integer employeeId, Integer cabinetId) {
        return employeeCabinetJoinRepository
                .findById(new EmployeeCabinetJoinPrimaryKey().setEmployeeId(employeeId).setCabinetId(cabinetId))
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeCabinetJoinEntity is not found"));
    }

    //  Установить логический статус соединения
    @Override
    public EmployeeCabinetJoinEntity setJoinStatus(Integer employeeId, Integer cabinetId, Boolean status) {
        EmployeeCabinetJoinEntity employeeCabinetJoinEntity = getById(employeeId, cabinetId);
        employeeCabinetJoinEntity.setActive(status);
        return employeeCabinetJoinRepository.save(employeeCabinetJoinEntity);
    }

    //  Физически удалить соединение
    @Override
    public void removeJoin(Integer employeeId, Integer cabinetId) {
        employeeCabinetJoinRepository.delete(getById(employeeId, cabinetId));
    }
}
