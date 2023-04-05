package com.example.demowithtests.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Класс, описывающий композитный первичный ключ join-таблицы для отношения ManyToMany Сотрудник - Кабинет
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class EmployeeCabinetJoinPrimaryKey implements Serializable {
    @Column(name = "user_id")
    private Integer employeeId;
    @Column(name = "cabinet_id")
    private Integer cabinetId;
}
