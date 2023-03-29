package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    //  Получить все свободные сущности Паспорт
    @Override
    public List<Passport> getAllFree() {
        return passportRepository.findAll().stream()
                .filter(e -> (e.getEmployee() == null))
                .collect(Collectors.toList());
    }

    //  Получить первую свободную сущность Паспорт
    @Override
    public Passport getFree() {
        return passportRepository.findAll().stream()
                .filter(e -> (e.getEmployee() == null))
                .findFirst().orElseThrow(() -> (new ResourceNotFoundException("Free Passport entity not found")));
    }

    //  Получить сущность Паспорт по идентификатору в бд
    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id).orElseThrow(() -> (new ResourceNotFoundException("Passport entity not found")));
    }

    //  Обновить сущность Паспорт
    @Override
    public Passport updateById(Integer id, Passport updatePassportParam) {
        Passport passport = passportRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        passport.setFirstName(updatePassportParam.getFirstName());
        passport.setSecondName(updatePassportParam.getSecondName());
        passport.setDateOfBirthday(updatePassportParam.getDateOfBirthday());
        passport.setExpireDate(updatePassportParam.getExpireDate());

        return passportRepository.save(passport);
    }

    //  Генерация серийного номера для нового паспорта
    private String generateSerialNumber() {
        return UUID.randomUUID().toString();
    }
}
