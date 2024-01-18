package br.com.vinisolon.application.validation;

import br.com.vinisolon.application.enums.ValidationMessagesEnum;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomStringValidator implements ConstraintValidator<CustomValidator, String> {

    private ValidationMessagesEnum message;
    private String validString;
    private int minSize;
    private int maxSize;

    @Override
    public void initialize(CustomValidator constraintAnnotation) {
        message = constraintAnnotation.value();
        minSize = constraintAnnotation.minSize();
        maxSize = constraintAnnotation.maxSize();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        log.info("[CustomObjectValidator] Validating String...");

        validString = str;

        buildConstraint(context);

        return isValidString();
    }

    private void buildConstraint(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message.getMessage())
                .addConstraintViolation();
    }

    private boolean isValidString() {
        return StringUtils.isNotEmpty(validString)
                && validString.length() >= minSize
                && validString.length() <= maxSize;
    }

}
