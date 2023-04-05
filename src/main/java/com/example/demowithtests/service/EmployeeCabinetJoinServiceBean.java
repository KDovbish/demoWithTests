package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeCabinetJoinEntity;
import com.example.demowithtests.repository.EmployeeCabinetJoinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeCabinetJoinServiceBean implements EmployeeCabinetJoinService{

    EmployeeCabinetJoinRepository employeeCabinetJoinRepository;

    @Override
    public EmployeeCabinetJoinEntity makeJoin(Employee employee, Cabinet cabinet) {
        EmployeeCabinetJoinEntity joinEntity = new EmployeeCabinetJoinEntity();
        joinEntity.setEmployee(employee);
        joinEntity.setCabinet(cabinet);
        joinEntity.setActive(Boolean.TRUE);
        return employeeCabinetJoinRepository.save(joinEntity);
    }
}
