package ru.cft.template.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.constants.regex.RegexPatterns;
import ru.cft.template.validation.BirthDate;
import ru.cft.template.validation.Patronymic;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
    @Pattern(regexp = RegexPatterns.FIRSTNAME_PATTERN, message = ValidationMessages.FIRSTNAME_CONTAINS_ONLY_LETTERS)
    @Length(max = 50, message = ValidationMessages.FIRSTNAME_LENGTH_CONSTRAINT)
    private String firstName;

    @Pattern(regexp = RegexPatterns.LASTNAME_PATTERN, message = ValidationMessages.LASTNAME_CONTAINS_ONLY_LETTERS)
    @Length(max = 50, message = ValidationMessages.LASTNAME_LENGTH_CONSTRAINT)
    private String lastName;

    @Patronymic
    private String patronymic;

    @Past(message = ValidationMessages.BIRTHDATE_PAST_REQUIRED)
    @BirthDate
    private LocalDate birthDate;
}
