package com.example.demowithtests;

import com.example.demowithtests.domain.EmployeeCabinetJoinEntity;
import com.example.demowithtests.domain.EmployeeCabinetJoinPrimaryKey;
import com.example.demowithtests.repository.EmployeeCabinetJoinRepository;

import com.example.demowithtests.service.EmployeeCabinetJoinServiceBean;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class EmployeeCabinetJoinServiceBeanTests {

    @Mock
    EmployeeCabinetJoinRepository employeeCabinetJoinRepository;

    @InjectMocks
    EmployeeCabinetJoinServiceBean employeeCabinetJoinService;

    @Test
    void getById() {

        EmployeeCabinetJoinEntity employeeCabinetJoinEntity = new EmployeeCabinetJoinEntity();

        when( employeeCabinetJoinRepository.findById(any(EmployeeCabinetJoinPrimaryKey.class)) ).thenReturn( Optional.of(employeeCabinetJoinEntity) );
        EmployeeCabinetJoinEntity joinEntity = employeeCabinetJoinService.getById(1, 1);
        assertThat(joinEntity).isSameAs(employeeCabinetJoinEntity);

        when( employeeCabinetJoinRepository.findById(any(EmployeeCabinetJoinPrimaryKey.class)) ).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeCabinetJoinService.getById(1, 1));

    }


    @Test
    void setJoinStatus() {
        EmployeeCabinetJoinEntity employeeCabinetJoinEntity = new EmployeeCabinetJoinEntity();
        employeeCabinetJoinEntity.setActive(Boolean.FALSE);

        //  Заглушка для того, чтобы отработал метод getById()
        when( employeeCabinetJoinRepository.findById(any(EmployeeCabinetJoinPrimaryKey.class)) ).thenReturn( Optional.of(employeeCabinetJoinEntity) );
        //  Заглушка для сохранения в репозитарий
        when( employeeCabinetJoinRepository.save(employeeCabinetJoinEntity) ).thenReturn(employeeCabinetJoinEntity);

        //  Внутри метода должен будет измениться статус
        employeeCabinetJoinService.setJoinStatus(1,1, Boolean.TRUE);
        //  Проверяем
        assertThat(employeeCabinetJoinEntity.getActive()).isTrue();
    }







}