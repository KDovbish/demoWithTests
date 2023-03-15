package com.example.demowithtests.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анотация, которая описывает ограничение уровня класса
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateEmployeeClassValidator.class)
public @interface ValidateEmployeeClass {
    String message() default "{ValidateEmployeeClass.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

