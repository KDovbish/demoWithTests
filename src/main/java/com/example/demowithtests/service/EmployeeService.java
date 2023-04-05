package com.example.demowithtests.service;

import com.example.demowithtests.domain.*;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PhotoCreateDto;
import com.example.demowithtests.dto.PhotoDto;
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

    /**
     * Получить список сотрудников, у которых либо просрочена либо в скором времени(параметр метода warnTreshold) будет просрочена фотография
     * @param storageDuration Допустимый срок хранения фотографии в годах
     * @param warnTreshold За сколько дней до окончания строка хранения фотографии делать предупреждение пользователю
     * @return
     */
    List<Employee> findExpiredPhotos(Integer storageDuration, Integer warnTreshold);

    /**
     * Обновить сущность Фотография
     * @param photoId id сущности Фотография для обновления
     * @param photoDto параметры, которые могут быть изменены в сущности Фотография
     * @return измененная сущность Фотография
     */
    Photo updatePhoto(Integer photoId, PhotoDto photoDto);

    /**
     * Добавить новое фото существующего сотрудника
     * @param employeeId id сущности Сотрудник
     * @param photo фотография для добавления
     * @return измененная сущность Сотрудник
     */
    Employee addNewEmployeePhoto(Integer employeeId, Photo photo);

    /**
     * Добавить jpeg-файл фотографии для сотрудника
     * @param photoId идентификатор сущности Фотография, в который нужно залить изображение
     * @param image массив байтов, составляющий jpeg-файл
     */
    void addPhotoImage(Integer photoId, byte[] image);

    /**
     * Получть из сущности Фотография массив байтов представляющих собой jpeg-файл
     * @param photoId идентификатор сущности Фотография
     * @return массив байтов, представляющих jpeg-файл
     */
    byte[] getPhotoImage(Integer photoId);

    /**
     * Связать сущность Сотрудник со свободным Паспорт из пула.
     * Если к Сотруднику уже привязан Паспорт, метод формирует цепочку паспортов
     * @param employeeId Идентификатор сущности Сотрудник в бд
     * @return Обновленная сущность Сотрудник
     */
    Employee addPassportToEmployee(Integer employeeId);

    /**
     * Обновить Паспорт сотрудника
     * @param employeeId Идентификатор сущности Сотрудник в бд
     * @param passport Данные паспорта для обновления
     * @return Обновленная сущность Паспорт в сущность Сотрудник
     */
    Employee updatePassport(Integer employeeId, Passport passport);

    /**
     * Изменить признак состояния сущности Паспорт, котороя привязана к Сотруднику
     * @param employeeId Идентификатор сущности Сотрудник в бд
     * @param passportState Новое состояние Паспорта
     * @return Обновленная сущность Паспорт в сущность Сотрудник
     */
    Employee changePassportState(Integer employeeId, PassportState passportState);

    /**
     * Получить цепочку Паспортов, которые когда либо были привязаны к сущности Сотрудник
     * @param employeeId Идентификатор сущности Сотрудник в бд
     * @return Цепочка Паспортов в виде списка
     */
    List<Passport> getPassportChain(Integer employeeId);


    /**
     * Связать сущность Сотрудник с Кабинетом
     * @param employeeId Идентификатор Сотрудника в бд
     * @param cabinetId Идентификатор Кабинета в бд
     * @return Обновленная сущность Сотрудник
     */
    Employee addEmployeeToCabinet(Integer employeeId, Integer cabinetId);







    /**
     * Добавить в существующую сущность Сотрудник связь на свободный Паспорт из пула свободных паспортов.
     * @param employeeId Идентификатор сущности Сотрудник в бд
     * @param passportParam Параметры паспорта, заданные на фронте, которые будут прописаны в Паспорт
     * @return Обновленная сущность Сотрудник
     */
    //Employee addPassportToEmployee(Integer employeeId, PassportRequestDto passportParam);








}
