package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.EmployeeCabinetJoinEntity;
import com.example.demowithtests.dto.CabinetResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface EmployeeCabinetJoinMapper {

    //  Откуда в сущности Join-таблицы брать параметры Кабинета
    @Mapping(target = ".", source = "cabinet")
    CabinetResponseDto employeeCabinetJoinEntityToCabinetResponseDto(EmployeeCabinetJoinEntity employeeCabinetJoinEntity);

    //  Кастомный метод преобразования коллекции сущностей Join-таблицы в коллекцию Кабинетов
    //  Написан, чтобы добавить контроль свойства active сущности Join-таблицы при добавлении каждого элемента в коллекцию Кабинетов
    default Set<CabinetResponseDto> employeeCabinetJoinEntitySetToCabinetResponseDtoSet(Set<EmployeeCabinetJoinEntity> joinEntities) {
        if (joinEntities == null) {
            return null;
        }

        Set<CabinetResponseDto> cabinetResponseDtoSet = new LinkedHashSet<CabinetResponseDto>( Math.max( (int) ( joinEntities.size() / .75f ) + 1, 16 ) );
        for ( EmployeeCabinetJoinEntity joinEntity : joinEntities ) {
            //  В коллекцию Кабинетов попадют только кабинеты из активных сущностей Join-таблицы
            if (joinEntity.getActive()) {
                cabinetResponseDtoSet.add( employeeCabinetJoinEntityToCabinetResponseDto( joinEntity ) );
            }
        }

        return cabinetResponseDtoSet;
    }

}
