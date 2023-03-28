package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PassportServiceBean implements PassportService {

    private final PassportRepository passportRepository;

    @Override
    public void generate(Integer n) {
        if (n > 0) {
            List<Passport> passportList = new ArrayList<>();
            Passport passport;
            for (int i = 1; i <= n; i++) {
                passport = new Passport();
                passport.setSerialNumber(generateSerialNumber());
                passportList.add(passport);
            }
            passportRepository.saveAll(passportList);
        }
    }

    //  Генерация серийного номера для нового паспорта
    private String generateSerialNumber() {
        return UUID.randomUUID().toString();
    }

}
