package com.example.demowithtests.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность join-таблицы для отношения ManyToMany Сотрудник - Кабинет
 */
@Entity
@Table(name = "users_cabinets")
@Getter
@Setter
public class EmployeeCabinetJoinEntity {
    @EmbeddedId
    EmployeeCabinetJoinPrimaryKey id = new EmployeeCabinetJoinPrimaryKey();

    @ManyToOne
    //  Первичный ключ сущности Сотрудник будет связан с внешним ключом в данной сущности в столбце user_id
    @JoinColumn(name = "user_id")
    //  Отобразить/Связать сущность Сотрудник через свойство employeeId композитного первичного ключа сущности join-таблицы
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne
    //  Первичный ключ сущности Кабинет будет связан с внешним ключом в данной сущности в столбце cabinet_id
    @JoinColumn(name = "cabinet_id")
    //  Отобразить/Связать сущность Кабинет через свойство cabinetId композитного первичного ключа сущности join-таблицы
    @MapsId("cabinetId")
    private Cabinet cabinet;

    //  Дополнительно введеный признак допустимости использования данного соединения
    Boolean active;
}
