package com.example.demowithtests.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Реализация логики проверки ограничения, заданного аннотацией CountryCode, для поля типа String
 */
public class CountryCodeValidator implements ConstraintValidator<CountryCode, String> {
    private String type;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        switch (type) {
            case "A2": return Pattern.compile("[a-zA-Z]{2}").matcher(s).matches();
            case "A3": return Pattern.compile("[a-zA-Z]{3}").matcher(s).matches();
            case "Number": return Pattern.compile("[0-9]{3}").matcher(s).matches();
            default: return Pattern.compile("[a-zA-Z]{2}").matcher(s).matches();
        }
    }

    @Override
    public void initialize(CountryCode constraintAnnotation) {
        type = constraintAnnotation.value();
    }
}