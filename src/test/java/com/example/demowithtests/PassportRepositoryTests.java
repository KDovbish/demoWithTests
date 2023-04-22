package com.example.demowithtests;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
public class PassportRepositoryTests {

    @Autowired
    PassportRepository passportRepository;

    @Test
    public void findById() {

        Passport passport = new Passport();
        passport.setSerialNumber("AB12CD45");
        passport = passportRepository.save(passport);

        //  Проверяем работу метода репозитария
        assertThat( passportRepository.findById(passport.getId()) ).isEqualTo(Optional.of(passport));
        assertThat( passportRepository.findById(passport.getId()).get() ).isSameAs(passport);
        assertThat( passportRepository.findById(passport.getId()).get().getSerialNumber() ).isEqualTo(passport.getSerialNumber());
    }


    @Test
    public void findAll() {
        passportRepository.saveAll(List.of(new Passport(), new Passport()));
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(2);
    }

}
