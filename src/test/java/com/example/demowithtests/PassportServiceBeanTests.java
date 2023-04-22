package com.example.demowithtests;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportState;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.service.PassportServiceBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassportServiceBeanTests {

    @Mock
    PassportRepository passportRepository;

    @InjectMocks
    PassportServiceBean passportServiceBean;

    @Test
    @DisplayName("get new and not deleted")
    public void getAllFree() {

        //  Готовим список паспортов для заглушки вызова репозитария
        List<Passport> passportList = new ArrayList<>();

        Passport passport = new Passport();
        passport.setState(PassportState.NEW);
        passport.setDeleted(Boolean.FALSE);
        passportList.add(passport);

        passport = new Passport();
        passport.setState(PassportState.EXPIRED);
        passport.setDeleted(Boolean.FALSE);

        //  Заглушка для вызова репозитария
        when(passportRepository.findAll()).thenReturn(passportList);

        //  Проверяем вызов метода, проверяем что фильтрация работает
        assertThat(passportServiceBean.getAllFree()).hasSize(1);

    }

    @Test
    public void getChain() {
        //  Готовим список паспортов для заглушки вызова репозитария
        List<Passport> passportList = new ArrayList<>();
        //  Заглушка для репозитария
        when( passportRepository.findPassportChain(anyInt()) ).thenReturn(passportList);
        //  Выполнение метода и проверка размера возвращаемого списка
        assertThat( passportServiceBean.getChain(1) ).hasSize(0);
        //  Проверка факта выполнения метода репозитария
        verify(passportRepository).findPassportChain(1);
    }

}
