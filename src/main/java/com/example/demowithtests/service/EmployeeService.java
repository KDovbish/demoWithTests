package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee create(@Valid Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    //Page<Employee> findByCountryContaining(String country, Pageable pageable);
    /**
     * @param country  Filter for the country if required
     * @param page            number of the page returned
     * @param size            number of entries in each page
     * @param sortList        list of columns to sort on
     * @param sortOrder       sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
    List<String> getAllEmployeeCountry();

    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
    List<String> getSortCountry();

    Optional<String> findEmails();


    /**
     * Получить всех пользователей, которые используют заданный почтовый домен
     * @param domain почтовый домен
     * @param city город; если указан, то отдаются только пользователи живующие в заданном городе
     * @return список пользователей
     */
    List<Employee> getByDomain(String domain, String city);

    /**
     * Получить всех пользоваталей заданного пола в пределах одного города
     * @param gender пол
     * @param city город
     * @return список пользователей
     */
    List<Employee> getCityGender(Gender gender, String city);

    /**
     * Прописать для заданного employee список пользователей, которым не разрешено с ним работать
     * @param id идентификатор employee
     * @param denyUsers список пользователей; в качестве разделителя - запятая
     * @return обновленный employee
     */
    Employee updateDenyUsersById(Integer id, String denyUsers);

    /**
     * Генерировать заданное количество сущностей Employee
     * @param quantity количество сущностей для генерации
     * @param clear перед генерацией очистить репозитарий
     */
    void generate(Integer quantity, boolean clear);

    /**
     * Обновить все сущности Employee
     */
    void updateAll();

}
