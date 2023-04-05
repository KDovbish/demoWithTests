package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.repository.CabinetRepositary;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CabinetServiceBean implements CabinetService {

    private final CabinetRepositary cabinetRepositary;

    //  Добавить в репозитарий новую сущность Кабинет
    @Override
    public Cabinet addCabinet(Cabinet cabinet) {
        return cabinetRepositary.save(cabinet);
    }

    //  Получить сущность Кабинет
    @Override
    public Cabinet getCabinet(Integer id) {
        Cabinet cabinet = cabinetRepositary.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cabinet entity is not found"));
        if (cabinet.getDeleted()) throw new ResourceNotFoundException("Cabinet entity is deleted");
        return cabinet;
    }

    //  Получить все неудаленные логически сущности Кабинет
    @Override
    public List<Cabinet> getAll() {
        return cabinetRepositary.findAll( Example.of(new Cabinet().setDeleted(Boolean.FALSE)) );
    }

}
