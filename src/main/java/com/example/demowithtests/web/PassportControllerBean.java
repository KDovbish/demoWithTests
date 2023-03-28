package com.example.demowithtests.web;

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

    @Override
    @PostMapping("/passports/{n}")
    @ResponseStatus(HttpStatus.CREATED)
    public void generate(@PathVariable Integer n) {
        passportService.generate(n);
    }

    @Override
    @GetMapping("/passports/free")
    @ResponseStatus(HttpStatus.OK)
    public List<PassportResponseDto> getAllFree() {
        return passportService.getAllFree().stream()
                .map(e -> (passportMapper.passportToPassportResponseDto(e)))
                .collect(Collectors.toList());
    }
}
