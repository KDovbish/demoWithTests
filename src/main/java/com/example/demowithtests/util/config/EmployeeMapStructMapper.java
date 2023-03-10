package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapStructMapper {
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    EmployeeDto employeeToEmployeeDto(Employee employee);
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);
}
