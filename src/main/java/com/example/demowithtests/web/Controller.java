package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.*;
import com.example.demowithtests.validation.ImageRestrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Controller {
    EmployeeDto saveEmployee(@Valid EmployeeCreateDto requestForSave);
    List<EmployeeReadDto> getAllUsers();
    Page<EmployeeReadDto> getPage(int page, int size);
    EmployeeReadDto getEmployeeById(Integer id);
    EmployeeDto refreshEmployee(Integer id, EmployeeDto employeeDto);
    void removeEmployeeById(Integer id);
    void removeAllUsers();
    public Page<EmployeeDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);
    List<String> getAllUsersC();
    List<String> getAllUsersSort();
    Optional<String> getAllUsersSo();
    List<EmployeeDto> findByDomain(String domain, String city);
    List<EmployeeDto> findByCityGender(Gender gender, String city);
    Employee updateDenyUsers(Integer id, String denyUsers);
    long generateEmployee(Integer quantity, boolean clear);
    long updateAllEmployeesByPUT();
    long updateAllEmployeesByPATCH();
    List<EmployeeDto> getEmployeesWithExpiriedPhoto();
    PhotoDto updatePhoto(Integer photoId, PhotoDto photoDto);
    EmployeeDto addPhoto(Integer employeeId, PhotoCreateDto photoCreateDto);
    void uploadPhoto(Integer photoId, @ImageRestrictions MultipartFile multipartFile) throws IOException;
    byte[] getPhotoImage(Integer photoId);
}
