package ru.cft.template.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.constants.regex.RegexPatterns;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Pattern(regexp = RegexPatterns.PHONE_NUMBER_PATTERN, message = ValidationMessages.PHONENUMBER_NOTCORRECT)
public @interface PhoneNumber {
    String message() default ValidationMessages.PHONENUMBER_NOTCORRECT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
