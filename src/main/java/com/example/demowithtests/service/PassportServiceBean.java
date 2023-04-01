package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportState;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceRemoveNotAllowedException;
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
                passport.setState(PassportState.NEW);
                passport.setDeleted(Boolean.FALSE);
                passportList.add(passport);
            }
            passportRepository.saveAll(passportList);
        }
    }

    //  Получить все свободные сущности Паспорт
    @Override
    public List<Passport> getAllFree() {
        return passportRepository.findAll().stream()
                .filter(e -> (e.getDeleted() == false))
                .filter(e -> (e.getState() == PassportState.NEW))
                .collect(Collectors.toList());
    }

    //  Получить первую свободную сущность Паспорт
    @Override
    public Passport getFree() {
        return passportRepository.findAll().stream()
                .filter(e -> (e.getDeleted() == false))
                .filter(e -> (e.getState() == PassportState.NEW))
                .findFirst().orElseThrow(() -> (new ResourceNotFoundException("Free Passport entity not found")));
    }

    //  Получить сущность Паспорт по идентификатору в бд
    @Override
    public Passport getById(Integer id) {
        Passport passport = passportRepository.findById(id).orElseThrow(() -> (new ResourceNotFoundException("Passport entity not found")));
        if (passport.getDeleted()) throw new ResourceNotFoundException("Passport entity is deleted");
        return passport;
    }

    //  Получить цепочку Паспортов, начиная с Паспорта с заданным идентификтором
    @Override
    public List<Passport> getChain(Integer id) {
        return passportRepository.findPassportChain(id);
    }


    //  Логическое удаление сущности Паспорт
    @Override
    public void removeById(Integer id) {

        //  Вызов getById() здесь нужен только для того чтобы генерировать разные исключения в случае отсутствия ресурса или его удаленности
        getById(id);

        //  В этой точке вызов getChain() отдаст как минимум одну запись, потому как Паспорт с заданным id существует
        List<Passport> passportChain = getChain(id);
        //  Метод getChain() всегда первым элементов в списке отдает элемент с запрошенным id, так как он является отправным для рекурсивного запроса.
        //  Если этот элемент находиться в начале цепочки, то только в этом случае возможно удаление
         if (passportChain.get(0).getNext() == null) {
             //  Связан ли удаляемый Паспорт с какой-либо сущностью Сотрудник? Если да, то разрываем связь
             if (passportChain.get(0).getEmployee() != null) {
                 passportChain.get(0).getEmployee().setPassport(null);
             }
             //  Удаляется вся цепочка
             passportChain.stream().forEach(e -> e.setDeleted(Boolean.TRUE));
             passportRepository.saveAll(passportChain);
         } else {
             //  Если удаляемый элемент находиться не в начале цепочки, генерируется исключение
             throw new ResourceRemoveNotAllowedException();
         }

    }




    //  Генерация серийного номера для нового паспорта
    private String generateSerialNumber() {
        return UUID.randomUUID().toString();
    }


/*
    //  Обновить сущность Паспорт
    @Override
    public Passport updateById(Integer id, Passport updatePassportParam) {
        Passport passport = getById(id);

        passport.setFirstName(updatePassportParam.getFirstName());
        passport.setSecondName(updatePassportParam.getSecondName());
        passport.setDateOfBirthday(updatePassportParam.getDateOfBirthday());
        passport.setExpireDate(updatePassportParam.getExpireDate());

        return passportRepository.save(passport);
    }
*/

}
