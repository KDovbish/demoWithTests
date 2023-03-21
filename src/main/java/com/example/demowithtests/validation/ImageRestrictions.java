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
    String message() default "Image parameters do not meet business requirements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int maxwidth() default 499;         //  обязательный параметр: максимально допустимая ширина изображения
    int maxheight() default 499;        //  обязательный параметр: максимально допустимая высота изображения
}
