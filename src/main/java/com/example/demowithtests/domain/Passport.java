package com.example.demowithtests.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passports")
@NoArgsConstructor
@Getter
@Setter
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String secondName;
    private LocalDateTime dateOfBirthday;
    private String serialNumber;
    private LocalDateTime expireDate;

    @OneToOne(mappedBy = "passport")
    private Employee employee;

    private Boolean deleted;
}
