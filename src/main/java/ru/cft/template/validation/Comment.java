package ru.cft.template.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.validation.validator.CommentValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CommentValidator.class})
public @interface Comment {
    String message() default ValidationMessages.COMMENT_LENGTH_CONSTRAINT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
