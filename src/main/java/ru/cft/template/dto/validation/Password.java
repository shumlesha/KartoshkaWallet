package ru.cft.template.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.validation.validator.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default ValidationMessages.PASSWORD_NOT_CORRECT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
