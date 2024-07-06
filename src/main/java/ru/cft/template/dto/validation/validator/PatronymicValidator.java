package ru.cft.template.dto.validation.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.constants.regex.RegexPatterns;
import ru.cft.template.dto.validation.Patronymic;

import java.util.regex.Pattern;

public class PatronymicValidator implements ConstraintValidator<Patronymic, String> {
    private static final Pattern PATRONYMIC_PATTERN = Pattern.compile(RegexPatterns.PATRONYMIC_PATTERN);

    @Override
    public boolean isValid(String patronymic, ConstraintValidatorContext context) {
        if (patronymic == null) {
            return true;
        }

        if (PATRONYMIC_PATTERN.matcher(patronymic).matches()) {
            if (patronymic.length() > 50) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ValidationMessages.PATRONYMIC_LENGTH_CONSTRAINT)
                        .addConstraintViolation();
                return false;
            }
            return true;
        }
        return false;
    }
}
