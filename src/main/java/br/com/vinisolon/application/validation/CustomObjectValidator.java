package br.com.vinisolon.application.validation;

import br.com.vinisolon.application.enums.ValidationMessagesEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CustomObjectValidator implements ConstraintValidator<CustomValidator, Object> {

    private ValidationMessagesEnum message;

    @Override
    public void initialize(CustomValidator constraintAnnotation) {
        message = constraintAnnotation.value();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        log.info("[CustomObjectValidator] Validating Object...");

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message.getMessage())
                .addConstraintViolation();

        return Objects.nonNull(obj);
    }

}
