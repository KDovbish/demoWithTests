package com.example.demowithtests.web;

import com.example.demowithtests.dto.CabinetCreateDto;
import com.example.demowithtests.dto.CabinetRequestDto;
import com.example.demowithtests.dto.CabinetResponseDto;
import com.example.demowithtests.service.CabinetService;
import com.example.demowithtests.util.config.CabinetMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CabinetControllerBean implements CabinetController {

    private final CabinetService cabinetService;
    private final CabinetMapper cabinetMapper;

    //  Создать Кабинет
    @Override
    @PostMapping("/cabinets")
    @ResponseStatus(HttpStatus.CREATED)
    public CabinetResponseDto createCabinet(@RequestBody CabinetCreateDto cabinetCreateDto) {
        return cabinetMapper.cabinetToCabinetResponseDto(
                cabinetService.addCabinet( cabinetMapper.cabinetCreateDtoToCabinet(cabinetCreateDto) )
        );
    }

    //  Получить Кабинет
    @Override
    @GetMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CabinetResponseDto readCabinet(@PathVariable Integer id) {
        return cabinetMapper.cabinetToCabinetResponseDto( cabinetService.getCabinet(id) );
    }

    //  Получить все Кабинеты
    @Override
    @GetMapping("/cabinets")
    @ResponseStatus(HttpStatus.OK)
    public List<CabinetResponseDto> readAllCabinet() {
        return cabinetService.getAll().stream()
                .map(e -> cabinetMapper.cabinetToCabinetResponseDto(e))
                .collect(Collectors.toList());
    }

    //  Логически удалить Кабинет
    @Override
    @PatchMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCabinet(@PathVariable Integer id) {
        cabinetService.removeCabinet(id);
    }

    //  Обновить параметры Кабинета
    @Override
    @PutMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CabinetResponseDto updateCabinet(@PathVariable Integer id, @RequestBody CabinetRequestDto cabinetRequestDto) {
        return cabinetMapper.cabinetToCabinetResponseDto(
                cabinetService.update(id, cabinetMapper.cabinetRequestDtoToCabinet(cabinetRequestDto))
        );
    }
}
