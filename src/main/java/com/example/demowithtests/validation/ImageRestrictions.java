package com.example.demowithtests.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анотация, устанавливающая бизнес-требования к изображению
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageRestrictionsValidator.class)
public @interface ImageRestrictions {
    String message() default "{ImageRestrictions.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int maxwidth();         //  обязательный параметр: максимально допустимая ширина изображения
    int maxheight();        //  обязательный параметр: максимально допустимая высота изображения
}
