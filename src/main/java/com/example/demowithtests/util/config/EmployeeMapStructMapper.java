package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface EmployeeMapStructMapper {

    //  Логика создания сущностей из DTO для сохранения в БД
    @Mapping(target = "isVisible", constant = "true")
    Employee employeeCreateDtoToEmployee(EmployeeCreateDto employeeCreateDto); // исходное свойство в dto отсутствует; целевое прописывается в любом случае

    @Mapping(target = "addDate", expression = "java( java.util.Date.from(java.time.Instant.now()) )")   // исходное свойство в dto отсутствует; целевое прописывается в любом случае
    Photo photoCreateDtoToPhoto(PhotoCreateDto photoDto);

    @Mapping(target = "addressHasActive", defaultValue = "true")  // целевое свойство прописывается только в случае если исходное null
    Address addressCreateDtoToAddress(AddressCreateDto addressCreateDto);


    //  Логика других преобразований
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    EmployeeDto employeeToEmployeeDto(Employee employee);
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);
    PhotoDto photoToPhotoDto(Photo photo);


}
