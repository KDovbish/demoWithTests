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
    @Query(value = "select distinct users.* from users, addresses " +
                    "where users.id = addresses.employee_id and " +
                    "users.email like ?1 and " +
                    "addresses.address_has_active = true and lower(addresses.city) = lower(?2)", nativeQuery = true)
    List<Employee> findByDomain(String emailPattern, String city);

    //  Выборка по почтовому домену
    @Query(value = "select * from users where email like ?1", nativeQuery = true)
    List<Employee> findByDomain(String emailPattern);

    //  Выборка по полу и городу
    @Query(value = "select distinct users.* from users join addresses on users.id = addresses.employee_id " +
                        "where users.gender = ?1 and " +
                                "addresses.address_has_active = true and lower(addresses.city) = lower(?2)", nativeQuery = true)
    List<Employee> findByCityGender(String gender, String city);

}
