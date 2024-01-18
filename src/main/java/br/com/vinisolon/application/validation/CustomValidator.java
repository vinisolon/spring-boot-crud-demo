package br.com.vinisolon.application.validation;

import br.com.vinisolon.application.enums.ValidationMessagesEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomObjectValidator.class, CustomStringValidator.class})
public @interface CustomValidator {

    ValidationMessagesEnum value();

    int minSize() default 0;

    int maxSize() default Integer.MAX_VALUE;

    String message() default "Required information is missing.";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

}
