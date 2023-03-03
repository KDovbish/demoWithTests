package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
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
        var employee = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

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
        return employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

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
    public Employee updateDenyUsersById(Integer id, String denyUsers) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> (new ResourceNotFoundException()));
        employee.setDenyUsers(denyUsers);
        return employeeRepository.save(employee);    }

}
