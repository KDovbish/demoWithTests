package com.example.demowithtests.web;

import com.example.demowithtests.service.PassportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PassportControllerBean implements PassportController {

    private final PassportService passportService;

    @Override
    @PostMapping("/passports/{n}")
    @ResponseStatus(HttpStatus.CREATED)
    public void generate(@PathVariable Integer n) {
        passportService.generate(n);
    }
}
