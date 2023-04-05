package com.example.demowithtests.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cabinets")
@NoArgsConstructor
@Getter
@Setter
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String name;
    private Integer capacity;

    //  Связь Один(первичный ключ Кабинета) ко Многим(внешний ключ столбца cabinet_id в join-таблице)
    @OneToMany(mappedBy = "cabinet")
    private Set<EmployeeCabinetJoinEntity> employeeCabinetJoinEntities = new HashSet<>();

    @Accessors(chain = true)
    private Boolean deleted;
}
