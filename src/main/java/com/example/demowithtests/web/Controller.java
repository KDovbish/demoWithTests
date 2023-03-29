package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.*;
import com.example.demowithtests.validation.ImageRestrictions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Controller {

    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists"),
            @ApiResponse(responseCode = "406", description = "NOT ACCEPTABLE. Validation failed")})
    EmployeeDto saveEmployee(@Valid EmployeeCreateDto requestForSave);

    List<EmployeeResponseDto> getAllUsers();
    Page<EmployeeReadDto> getPage(int page, int size);
    EmployeeResponseDto getEmployeeById(Integer id);
    EmployeeDto refreshEmployee(Integer id, EmployeeDto employeeDto);
    void removeEmployeeById(Integer id);
    void removeAllUsers();
    public Page<EmployeeDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);
    List<String> getAllUsersC();
    List<String> getAllUsersSort();
    Optional<String> getAllUsersSo();


    /**
     * Отдать всех пользователей, использующих заданный почтовый домен
     * @param domain почтовой домен
     * @param city город; если указан, то отдаются почтовые домены в пределах заданного города
     * @return список пользоваталей
     */
    @Operation(summary = "Поиск сотрудников по почтовому домену", description = "Получить всех сотрудников либо в разрезе по городу, которые используют заданный почтовый домен", tags = {"Employee"})
    @Parameters(value = {
            @Parameter(name = "domain", description = "Почтовый домен емейла"),
            @Parameter(name = "city", description = "Город", required = false)
    })
    List<EmployeeDto> findByDomain(String domain, String city);

    /**
     * Отдать всех пользователей заданного пола в пределах заданного города
     * @param gender пол
     * @param city город
     * @return список пользователей
     */
    @Operation(summary = "Поиск сотрудников заданного пола в заданном городе", description = "Получить всех сотрудников, имеющих заданный пол и актиный адрес в заданном городе", tags = {"Employee"})
    List<EmployeeDto> findByCityGender(Gender gender, String city);

    /**
     * Прописать новое содержимое в столбец denyUsers - список пользователей, которым запрещено использование заданного employee
     * @param id идентификатор обновляемого employee
     * @param denyUsers список пользователей
     * @return обновленный employee
     */
    @Operation(summary = "Обновить список пользователей, которым блокирован доступ к заданной сущности Сотрудник",
                description = "Обновление поля denyUsers. В качестве разделителя имен пользователей, использовать запятую", tags = {"Employee"})
    @Parameters(value = {
            @Parameter(name = "id", description = "Идентификатор сущности Сотрудник"),
            @Parameter(name = "denyUsers", description = "Список пользоваталей. Пример: user,admin")
    })
    Employee updateDenyUsers(Integer id, String denyUsers);

    /**
     * Автоматическая генерация сущностей Employee
     * @param quantity количество генерируемых сущностей
     * @param clear очистить репозитарий перед генерацией
     * @return время выполнения операции в милисекундах
     */
    @Operation(summary = "Автоматическая генерация сущностей Сотрудник", description = "Автоматическая генерация сущностей Сотрудник", tags = {"Employee"})
    @Parameters(value = {
            @Parameter(name = "quantity", description = "Количество сущностей для генерации"),
            @Parameter(name = "clear", description = "Очищать ли перед генерацией репозитарий")
    })
    @ApiResponse(responseCode = "201", description = "Сущности созданы. В теле ответа возвращено время их создания в милисекундах")
    long generateEmployee(Integer quantity, boolean clear);

    /**
     * Массовое обновление всех сущностей Employee(метод PUT)
     * @return время выполнения операции в милисекундах
     */
    @Operation(summary = "Массовое обновление сотрудников методом PUT", description = "Массовое обновление сотрудников методом PUT", tags = {"Employee"})
    @ApiResponse(responseCode = "200", description = "Сущности обновлены. В теле ответа возвращено время их обновления в милисекундах")
    long updateAllEmployeesByPUT();

    /**
     * Массовое обновление всех сущностей Employee(метод PATCH)
     * @return время выполнения операции в милисекундах
     */
    @Operation(summary = "Массовое обновление сотрудников методом PATCH", description = "Массовое обновление сотрудников методом PATCH", tags = {"Employee"})
    @ApiResponse(responseCode = "200", description = "Сущности обновлены. В теле ответа возвращено время их обновления в милисекундах")
    long updateAllEmployeesByPATCH();


    /**
     * Получить список сотрудников у которых есть хотя бы одно просроченое фото
     * @return Выбранный список сотрудников
     */
    @Operation(summary = "Получить всех Сотрудников у которых просрочена фотография", description = "Получить всех Сотрудников у которых просрочена фотография", tags = {"Employee"})
    List<EmployeeDto> getEmployeesWithExpiriedPhoto();

    /**
     * Обновление сущности Фотография
     * @param photoId id сущности Фотография
     * @param photoDto объект описывающий параметры которые могут быть изменены в сущности Фотография
     * @return dto измененной сущности Фотография
     */
    @Operation(summary = "Обность сущность Фотография", description = "Обновить сущность Фотография", tags = {"Photo"})
    PhotoDto updatePhoto(Integer photoId, PhotoDto photoDto);

    /**
     * Добавить еще одну новую сущность Фотография уже существующему сотруднику
     * @param employeeId id Сотрудника
     * @param photoCreateDto Описание новой добавляемой сущности Фотография
     * @return Обновленная сущность Сотрудник
     */
    @Operation(summary = "Добавить сущность Фотография в сущность Сотрудник", description = "Добавить для существующего Сотрудника новую сущность Фотография", tags = {"Photo"})
    EmployeeDto addPhoto(Integer employeeId, PhotoCreateDto photoCreateDto);


    /**
     * Загрузка файла фотографии в уже существующую сущность Фотография
     * @param photoId Идентификатор Фотографии
     * @param multipartFile Часть составного содержимого http-запроса с jpeg-файлом
     * @throws IOException
     */
    @Operation(summary = "Загрузить файл фотографии", description = "Загрузить данные файла изображения в сущность Фотография", tags = {"Photo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Идентификатор сущности Фотография не найден")
    })
    void uploadPhoto(Integer photoId, @ImageRestrictions MultipartFile multipartFile) throws IOException;


    /**
     * Получить файл фотографии
     * @param photoId Идентификатор сущности Фотография
     * @return массив байтов, составляющих файл фотографии
     */
    @Operation(summary = "Запросить файл фотографии", description = "Запросить данные файла изображения, по идентификатору сущности Фотография", tags = {"Photo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Идентификатор сущности Фотография не найден")
    })
    byte[] getPhotoImage(Integer photoId);

    /**
     * Добавить в сущность Сотрудника связь на Паспорт
     * @param employeeId Идентификатор уже существующего Сотрудника в бд
     * @param passportId Идентификатор уже существующего Паспорта в бд
     * @return DTO обновленной сущности Сотрудник
     */
    EmployeeResponseDto addPassport(Integer employeeId, Integer passportId);

    /**
     * Добавить в сущность Сотрудника связь на первый свободный Паспорт.
     * Метод самостоятельно находит свободный паспорт и заполняет его в соответствии с параметрыми, переданными фронтом.
     * @param employeeId Идентификатор Сотрудника в бд
     * @param pasportRequestDto Параметры Паспорта, которые заполняются на фронте
     * @return DTO обновленной сущности Сотрудник
     */
    EmployeeResponseDto addPassport(Integer employeeId, PassportRequestDto pasportRequestDto);


}
