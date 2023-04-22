package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DisplayName("findByDomain JPQL")
    public void findByDomain() {

        Employee employee = new Employee();
        employee.setEmail("iivanov@gmail.com");
        employee = employeeRepository.save(employee);

        //  Проверка работоспособности запроса выборки по почтовому домену
        Assertions.assertThat( employeeRepository.findByDomain("%@gmail.com") ).hasSize(1);
        Assertions.assertThat( employeeRepository.findByDomain("%@ukr.net") ).hasSize(0);
    }


    @Test
    public void findByName() {
        Employee employee = new Employee();
        employee.setName("Ivan Ivanov");
        employee = employeeRepository.save(employee);

        //  Проверяем отработку метода findByName(), беря имя из вычитанной сущности из репозитария
        Assertions.assertThat( employeeRepository.findByName(employeeRepository.findById(employee.getId()).orElseThrow().getName()).getName() ).isEqualTo(employee.getName());
    }

}
