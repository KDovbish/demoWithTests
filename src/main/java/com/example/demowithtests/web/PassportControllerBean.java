package com.example.demowithtests.web;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import com.example.demowithtests.service.PassportService;
import com.example.demowithtests.util.config.PassportMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PassportControllerBean implements PassportController {

    private final PassportService passportService;
    private final PassportMapper passportMapper;

    //  Создать заданное количество сущностей Паспорт в бд
    @Override
    @PostMapping("/passports/{n}")
    @ResponseStatus(HttpStatus.CREATED)
    public void generate(@PathVariable Integer n) {
        passportService.generate(n);
    }

    //  Получить все свободные Паспорта, которые можно привязывать к Сотрудникам
    @Override
    @GetMapping("/passports/free")
    @ResponseStatus(HttpStatus.OK)
    public List<PassportResponseDto> getAllFree() {
        return passportService.getAllFree().stream()
                .map(e -> (passportMapper.passportToPassportResponseDto(e)))
                .collect(Collectors.toList());
    }

    //  Получить Паспорт по идентификатору в бд
    @Override
    @GetMapping("/passports/{passportId}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponseDto getPassportById(@PathVariable Integer passportId) {
        return passportMapper.passportToPassportResponseDto(passportService.getById(passportId));
    }

    //  Логическое удаление сущности Паспорт
    @Override
    @PatchMapping("/passports/{passportId}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable Integer passportId) {
        passportService.removeById(passportId);
    }



    /*
    //  Изменить заданную сущность Паспорт
    @Override
    @PutMapping("/passports/{passportId}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponseDto update(@PathVariable Integer passportId, @RequestBody PassportRequestDto passportRequestDto) {
        Passport passport = passportService.updateById(passportId, passportMapper.passportRequestDtoToPassport(passportRequestDto));
        return passportMapper.passportToPassportResponseDto(passport);
    }
    */

}
