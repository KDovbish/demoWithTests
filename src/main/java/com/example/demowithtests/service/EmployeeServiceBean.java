package com.example.demowithtests.service;

import com.example.demowithtests.domain.*;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PhotoCreateDto;
import com.example.demowithtests.dto.PhotoDto;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PhotoRepositary;
import com.example.demowithtests.util.exception.EntityAccessDeniedException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final GenerateRandom generateRandom;
    private final PhotoRepositary photoRepositary;
    private final PassportService passportService;
    private final CabinetService cabinetService;
    private final EmployeeCabinetJoinService employeeCabinetJoinService;

    @Override
    public Employee create(@Valid Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee entity not found"));

        //  Замена null значения isVisible на true
        changeStatus(employee);

        //  "Стандартный" функционал начинает работать только в случае, если текущему, прошедшему аутентификацию, пользователю,
        //  разрешен доступ к данной сущности
        if (isCurrentUserAccess(employee)) {
            if (employee.getVisible() == false) {
                throw new EntityNotFoundException("Employee was deleted with id = " + id);
            }
        } else {
            throw new EntityAccessDeniedException("Access denied to entity. Type entity: Employee. id: " + employee.getId());
        }

        return employee;
    }

    private void changeStatus(Employee employee) {
        log.info("changeStatus(Employee employee) Service - start: id = {}", employee.getId());
        if (employee.getVisible() == null) {
            employee.setVisible(Boolean.TRUE);
            employeeRepository.save(employee);
        }
        log.info("changeStatus(Employee employee) Service - end: isVisible = {}", employee.getVisible());
    }

    /**
     * Проверить, разрешен ли текущему пользователю доступ к сущности
     * @param employee проверяемая на доступ сущность
     * @return true/false
     */
    private boolean isCurrentUserAccess(Employee employee) {
        if (employee.getDenyUsers() == null) return true;
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return !Arrays.stream(employee.getDenyUsers().split(","))
                .map(e -> e.trim())
                .anyMatch(e -> e.equalsIgnoreCase(currentUser));
    }


    @Override
    public Employee updateById(Integer id, Employee employee) {
        Employee entity = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        if (isCurrentUserAccess(entity)) {
            if (entity.getVisible() == false) {
                throw new EntityNotFoundException("Employee was deleted with id = " + id);
            } else {
                entity.setName(employee.getName());
                entity.setEmail(employee.getEmail());
                entity.setCountry(employee.getCountry());
                return employeeRepository.save(entity);
            }
        } else {
            throw new EntityAccessDeniedException();
        }
    }


/*
    private void entityAccessValidation(Employee employee) {
        if (isCurrentUserAccess(employee)) {
            if (employee.getVisible() == false) {
                throw new EntityNotFoundException("Employee was deleted with id = " + id);
            }
        } else {
            throw new EntityAccessDeniedException();
        }
    }
*/




    @Override
    public void removeById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (isCurrentUserAccess(employee)) {
            if (employee.getVisible() == false) {
                throw new EntityNotFoundException("Employee was alredy deleted with id = " + id);
            } else {
                employee.setVisible(Boolean.FALSE);
                employeeRepository.save(employee);
            }
        } else {
            throw new EntityAccessDeniedException();
        }
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }

    /*@Override
    public Page<Employee> findByCountryContaining(String country, Pageable pageable) {
        return employeeRepository.findByCountryContaining(country, pageable);
    }*/

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());
        /*List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());*/

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }


    //  Какие пользователи используют заданный почтовый домен?
    @Override
    public List<Employee> getByDomain(String domain, String city) {
        if (city == null || city.equals("")) return employeeRepository.findByDomain("%@" + domain);
         else return employeeRepository.findByDomain("%@" + domain, city);
    }

    //  Кто из мужчин/женщин проживает в заданном городе?
    @Override
    public List<Employee> getCityGender(Gender gender, String city) {
        return employeeRepository.findByCityGender(gender, city);
    }


    //  Прописать пользователей, которым запрещено выполнять какие-либо действия с заданным employee
    @Override
    public Employee updateDenyUsersById(Integer id, String denyUsers) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> (new ResourceNotFoundException()));
        employee.setDenyUsers(denyUsers);
        return employeeRepository.save(employee);
    }

    //  Генерировать сущности Employee
    @Override
    public void generate(Integer quantity, boolean clear) {
        log.info("EmployeeServiceBean  generate()  clear: " + clear);
        if (clear) employeeRepository.deleteAll();
        for (int i = 1; i <= quantity; i++) {
            employeeRepository.save(generateRandom.getEmployee());
        }
    }

    //  Обновить все сущности Employee
    @Override
    public void updateAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        employeeList.forEach(e -> e.setName(e.getName() + " CHANGED"));
        employeeRepository.saveAll(employeeList);
    }



    //  Получить все объекты Employee, у которых просрочена фото
    @Override
    public List<Employee> findExpiredPhotos(Integer storageDuration, Integer warnTreshold) {

        List<Employee> empoyeeWithExpiredPhoto = new ArrayList<>();

        //  Получаем дату, которая является порогом для даты истечения срока хранения фото
        //  Если дата порога еще не достигла дата истечения срока хранения фото, значит фотография еще действительна
        //  Если дата порога достигла или превысила дату истечения срока хранения фото, то требуется уведомлять пользователя о необходимости смены фото
        Calendar tresholdDate = DatePlusDays(Date.from(Instant.now()), warnTreshold);

        //  Из базы выбираются все сотрудники
        //  Производиться поиск сотрудников, с устаревшими фото
        List<Employee> employeeList = employeeRepository.findAll();
        for(Employee employee: employeeList) {
            for (Photo photo: employee.getPhotos()) {
                //  Если пороговая дата достикла или превысила конечную возможную дату хранения фото...
                if ( tresholdDate.after( DatePlusYears(photo.getAddDate(), storageDuration )  ) ) {
                    empoyeeWithExpiredPhoto.add(employee);
                }
            }
        }

        //  Удаление дублирующихся объектов типа Employee
        //  Такая ситуация возможна, поскольку в классе Employee прописана связь один ко многим. Т.е. один сотрудник может иметь несколько фото
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.addAll(empoyeeWithExpiredPhoto);

        return new ArrayList<Employee>(employeeSet);
    }


    /**
     * К значению типа Date прибавить заданное количесто лет
     * @param date Date-значение
     * @param years Количество лет для прибавления
     * @return Значение типа Calendar
     */
    private Calendar DatePlusYears(Date date, Integer years) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        calendar.add(Calendar.YEAR, years);
        return calendar;
    }

    /**
     * К значению типа Date прибавать заданное количество дней
     * @param date Date-значение
     * @param days Количетсво дней для прибавления
     * @return Значение типа Calendar
     */
    private Calendar DatePlusDays(Date date, Integer days) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar;
    }


    //  Обновить сущестующую фото
    @Override
    public Photo updatePhoto(Integer photoId, PhotoDto photoDto) {
        Photo photo = photoRepositary.findById(photoId).orElseThrow( () -> new ResourceNotFoundException() );

        if (photoDto.description != null) photo.setDescription(photoDto.description);
        if (photoDto.cameraType != null) photo.setCameraType(photoDto.cameraType);
        if (photoDto.photoUrl != null) {
            photo.setPhotoUrl(photoDto.photoUrl);
            photo.setAddDate(Date.from(Instant.now()));
        }

        return photoRepositary.save(photo);
    }

    //  Добавить новое фото существующему сотруднику
    @Override
    public Employee addNewEmployeePhoto(Integer employeeId, Photo photo) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow( () -> new ResourceNotFoundException() );
        employee.getPhotos().add(photo);
        return employeeRepository.save(employee);
    }

    //  Загрузить файл фотографии
    @Override
    public void addPhotoImage(Integer photoId, byte[] image){
        Photo photo = photoRepositary.findById(photoId).orElseThrow( () -> new ResourceNotFoundException() );
        photo.setImage(image);
        photoRepositary.save(photo);
    }

    //  Получить файл фотографии
    @Override
    public byte[] getPhotoImage(Integer photoId) {
        Photo photo = photoRepositary.findById(photoId).orElseThrow( () -> new ResourceNotFoundException() );
        return photo.getImage();
    }

    //  Связать сущность Сотрудник с первым свободным Паспортом из пула
    //  Если к Сотруднику уже привязан какой-либо Паспорт, то метод формирует цепочку Паспортов
    @Override
    public Employee addPassportToEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->(new ResourceNotFoundException("Employee entity not found")));
        Passport freePassport = passportService.getFree();

        //  Начинаем цепочку паспортов только в том случае, если у Сотрудника уже существует какой-то паспорт
        if (employee.getPassport() != null) {
            employee.getPassport().setNext(freePassport);
            freePassport.setPrev(employee.getPassport());
        }

        employee.setPassport(freePassport);
        employee.getPassport().setState(PassportState.ACTIVE);
        return employeeRepository.save(employee);
    }

    //  Обновить Паспорт Сотрудника
    @Override
    public Employee updatePassport(Integer employeeId, Passport passport) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->(new ResourceNotFoundException("Employee entity not found")));
        if (employee.getPassport() == null) throw new ResourceNotFoundException("Passport entity is not found for Employee entity");

        employee.getPassport().setFirstName(passport.getFirstName());
        employee.getPassport().setSecondName(passport.getSecondName());
        employee.getPassport().setDateOfBirthday(passport.getDateOfBirthday());
        employee.getPassport().setExpireDate(passport.getExpireDate());

        return employeeRepository.save(employee);
    }

    //  Изменить признак состояния Паспорта Сотрудника
    @Override
    public Employee changePassportState(Integer employeeId, PassportState passportState) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->(new ResourceNotFoundException("Employee entity not found")));
        if (employee.getPassport() == null) throw new ResourceNotFoundException("Passport entity is not found for Employee entity");
        employee.getPassport().setState(passportState);
        return employeeRepository.save(employee);
    }

    //  Получить цепочку Паспортов, закрепленных за Сотрдником
    @Override
    public List<Passport> getPassportChain(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->(new ResourceNotFoundException("Employee entity not found")));
        if (employee.getPassport() == null) throw new ResourceNotFoundException("Passport entity is not found for Employee entity");
        return passportService.getChain(employee.getPassport().getId());
    }

    //  Связать Сотрудника с Кабинетом
    @Override
    public Employee addEmployeeToCabinet(Integer employeeId, Integer cabinetId) {
        Employee employee = getById(employeeId);
        Cabinet cabinet = cabinetService.getCabinet(cabinetId);
        employeeCabinetJoinService.makeJoin(employee, cabinet);
        return employee;
    }



    /*
    //  Связать существующую сущность Сотрудник с первым свободным Паспортом
    @Override
    public Employee addPassportToEmployee(Integer employeeId, PassportRequestDto passportParam) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->(new ResourceNotFoundException("Employee entity not found")));
        Passport passport = passportService.getFree();

        passport.setFirstName(passportParam.firstName);
        passport.setSecondName(passportParam.secondName);
        passport.setDateOfBirthday(passportParam.dateOfBirthday);
        passport.setExpireDate(passportParam.expireDate);

        employee.setPassport(passport);
        return employeeRepository.save(employee);
    }
*/
}
