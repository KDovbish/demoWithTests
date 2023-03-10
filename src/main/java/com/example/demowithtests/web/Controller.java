package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
//import com.example.demowithtests.util.config.EmployeeConverter;
import com.example.demowithtests.util.config.EmployeeMapStructMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class Controller {

    private final EmployeeService employeeService;
    //private final EmployeeConverter converter;
    private final EmployeeMapStructMapper employeeMapStructMapper;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {

        //var employee = converter.getMapperFacade().map(requestForSave, Employee.class);
        //  Замена функионала Orika на MapStruct
        //  EmployeeDto -> Employee
        var employee = employeeMapStructMapper.employeeDtoToEmployee(requestForSave);

        //var dto = converter.toDto();
        //  Замена функионала Orika на MapStruct
        //  Employee -> EmployeeDto
        var dto = employeeMapStructMapper.employeeToEmployeeDto(employeeService.create(employee));

        return dto;
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return employeeService.getAll().stream()
                .map(e -> employeeMapStructMapper.employeeToEmployeeReadDto(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging)
                .map(e -> employeeMapStructMapper.employeeToEmployeeReadDto(e));
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.debug("getEmployeeById() Controller - start: id = {}", id);
        var employee = employeeService.getById(id);
        log.debug("getById() Controller - to dto start: id = {}", id);

        //var dto = converter.toReadDto(employee);
        //  Замена функионала Orika на MapStruct
        //  Employee -> EmployeeReadDto
        var dto = employeeMapStructMapper.employeeToEmployeeReadDto(employee);

        log.debug("getEmployeeById() Controller - end: name = {}", dto.name);
        return dto;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
        return employeeMapStructMapper.employeeToEmployeeDto(
                    employeeService.updateById(id, employeeMapStructMapper.employeeDtoToEmployee(employeeDto))
                    );
    }

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeDto> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());


        return employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString())
                .map(e -> employeeMapStructMapper.employeeToEmployeeDto(e));
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }


    /**
     * Отдать всех пользователей, использующих заданный почтовый домен
     * @param domain почтовой домен
     * @param city город; если указан, то отдаются почтовые домены в пределах заданного города
     * @return список пользоваталей
     */
    @GetMapping("/users/domain")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> findByDomain(@RequestParam String domain, @RequestParam(required = false) String city) {
        return employeeService.getByDomain(domain, city).stream()
                .map(e -> employeeMapStructMapper.employeeToEmployeeDto(e))
                .collect(Collectors.toList());
    }

    /**
     * Отдать всех пользователей заданного пола в пределах заданного города
     * @param gender пол
     * @param city город
     * @return список пользователей
     */
    @GetMapping("/users/citygender")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> findByCityGender(@RequestParam Gender gender, @RequestParam String city) {
        return employeeService.getCityGender(gender, city).stream()
                .map(e -> employeeMapStructMapper.employeeToEmployeeDto(e))
                .collect(Collectors.toList());
    }


    /**
     * Прописать новое содержимое в столбец denyUsers - список пользователей, которым запрещено использование заданного employee
     * @param id идентификатор обновляемого employee
     * @param denyUsers список пользователей
     * @return обновленный employee
     */
    @PutMapping("/users/deny/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateDenyUsers(@PathVariable Integer id, @RequestParam("users") String denyUsers) {
        return employeeService.updateDenyUsersById(id, denyUsers);
    }


    /**
     * Автоматическая генерация сущностей Employee
     * @param quantity количество генерируемых сущностей
     * @param clear очистить репозитарий перед генерацией
     * @return время выполнения операции в милисекундах
     */
    @PostMapping("/users/generate/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public long generateEmployee(@PathVariable Integer quantity, @RequestParam(required = false, defaultValue = "false") boolean clear) {
        Date startTime = Date.from(Instant.now());
        employeeService.generate(quantity, clear);
        return (Date.from(Instant.now()).getTime() - startTime.getTime());
    }

    /**
     * Массовое обновление всех сущностей Employee(метод PUT)
     * @return время выполнения операции в милисекундах
     */
    @PutMapping("/users/updateall")
    @ResponseStatus(HttpStatus.OK)
    public long updateAllEmployeesByPUT() {
        Date startTime = Date.from(Instant.now());
        employeeService.updateAll();
        return (Date.from(Instant.now()).getTime() - startTime.getTime());
    }

    /**
     * Массовое обновление всех сущностей Employee(метод PATCH)
     * @return время выполнения операции в милисекундах
     */
    @PatchMapping("/users/updateall")
    @ResponseStatus(HttpStatus.OK)
    public long updateAllEmployeesByPATCH() {
        Date startTime = Date.from(Instant.now());
        employeeService.updateAll();
        return (Date.from(Instant.now()).getTime() - startTime.getTime());
    }



}
