package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
//@Component
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);


    //  Выборка по почтовому домену и городу
    @Query(value = "select distinct e from Employee e join e.addresses a " +
                        "on e.email like ?1 and lower(a.city) = lower(?2) and a.addressHasActive = true")
    List<Employee> findByDomain(String emailPattern, String city);

    //  Выборка по почтовому домену
    @Query(value = "select e from Employee e where e.email like ?1")
    List<Employee> findByDomain(String emailPattern);

    //  Выборка по полу и городу
    @Query("select distinct e from Employee e join e.addresses a " +
                "on e.gender = ?1 and a.addressHasActive = true and lower(a.city) = lower(?2)")
    List<Employee> findByCityGender(Gender gender, String city);

}
