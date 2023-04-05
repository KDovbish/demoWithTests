package com.example.demowithtests.domain;

import com.example.demowithtests.validation.UpperCase;
import com.example.demowithtests.validation.ValidateEmployeeClass;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@ValidateEmployeeClass
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @UpperCase
    private String country;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean visible;
    private String denyUsers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Photo> photos = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    //  Связь Один(первичный ключ Сотрудника) ко Многим(внешний ключ столбца user_id в join-таблице)
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeCabinetJoinEntity> employeeCabinetJoinEntities = new HashSet<>();

}
