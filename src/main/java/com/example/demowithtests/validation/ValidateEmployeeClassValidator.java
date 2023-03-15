package com.example.demowithtests.validation;

import com.example.demowithtests.domain.Employee;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Реализация ограничения, заданного аннотацией ValidateEmployeeClass, для объекта типа Employee
 */
public class ValidateEmployeeClassValidator implements ConstraintValidator<ValidateEmployeeClass, Employee> {
    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext constraintValidatorContext) {

        try {

            Method getter, setter;
            String getterName, setterName;
            String value;

            //  Перебираем все поля класса Employee
            for (Field field: employee.getClass().getDeclaredFields()) {
                //  Каждое поле проверяем на наличие анотации UpperCase
                if (field.isAnnotationPresent(UpperCase.class)) {
                    //  Если поле имеет стринговый тип, то модифицируем значение этого поля в соответствии
                    //  с задуманной функциональностью аннотации UpperCase
                    if (field.getType().getName().equals("java.lang.String")) {

                        //  Получаем имена геттера и сеттера для поля
                        getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                        //  Получаем ссылки на объекты, описывающие геттер и сеттер
                        getter = employee.getClass().getMethod(getterName);
                        setter = employee.getClass().getMethod(setterName, String.class);

                        //  Только в случае если текущее значение поля не null, применяем к полю функционал, который
                        //  подразумевает анотация UpperCase
                        if ( (value = ((String) getter.invoke(employee))) != null ) {
                            setter.invoke(employee, value.toUpperCase());
                        }

                    }
                }
            }

        } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }
}