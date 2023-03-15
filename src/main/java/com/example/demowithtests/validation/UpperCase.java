package com.example.demowithtests.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, применимая к стринговому полю и являющаяся сигнальным признаком того, что значение данного поля
 * должно быть преобразовано в верхних регистр.
 * Реализация функционала, которого требует данная аннотация, выполнена в обработчике аннотации уровня класса @ValidateEmployeeClass.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpperCase {
}
