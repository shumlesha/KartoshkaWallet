package ru.cft.template.dto.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.validation.Comment;

public class CommentValidator implements ConstraintValidator<Comment, String> {
    @Override
    public boolean isValid(String comment, ConstraintValidatorContext context) {
        if (comment == null) {
            return true;
        }
        if (comment.length() > 250) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidationMessages.PATRONYMIC_LENGTH_CONSTRAINT)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
