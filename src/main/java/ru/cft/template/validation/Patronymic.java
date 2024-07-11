package ru.cft.template.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.validation.validator.PatronymicValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatronymicValidator.class)
public @interface Patronymic {
    String message() default ValidationMessages.PATRONYMIC_NOT_CORRECT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
