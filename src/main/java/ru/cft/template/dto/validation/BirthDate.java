package ru.cft.template.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.validation.validator.BirthDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDate {
    String message() default ValidationMessages.TOO_OLD_CONSTRAINT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
