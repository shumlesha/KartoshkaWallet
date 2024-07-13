package ru.cft.template.dto.user;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.constants.RegexPatterns;
import ru.cft.template.validation.BirthDate;
import ru.cft.template.validation.Password;
import ru.cft.template.validation.Patronymic;
import ru.cft.template.validation.PhoneNumber;

import java.time.LocalDate;

@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotNull(message = ValidationMessages.FIRSTNAME_NOTNULL_REQUIRED)
    @NotBlank(message = ValidationMessages.FIRSTNAME_NOTBLANK_REQUIRED)
    @Pattern(regexp = RegexPatterns.FIRSTNAME_PATTERN, message = ValidationMessages.FIRSTNAME_CONTAINS_ONLY_LETTERS)
    @Length(max = 50, message = ValidationMessages.FIRSTNAME_LENGTH_CONSTRAINT)
    private String firstName;

    @NotNull(message = ValidationMessages.LASTNAME_NOTNULL_REQUIRED)
    @NotBlank(message = ValidationMessages.LASTNAME_NOTBLANK_REQUIRED)
    @Pattern(regexp = RegexPatterns.LASTNAME_PATTERN, message = ValidationMessages.LASTNAME_CONTAINS_ONLY_LETTERS)
    @Length(max = 50, message = ValidationMessages.LASTNAME_LENGTH_CONSTRAINT)
    private String lastName;

    @Patronymic
    private String patronymic;

    @PhoneNumber
    private String phoneNumber;

    @Email(regexp = RegexPatterns.EMAIL_PATTERN,
            message = ValidationMessages.EMAIL_VALID_REQUIRED)
    private String email;

    @Past(message = ValidationMessages.BIRTHDATE_PAST_REQUIRED)
    @NotNull(message = ValidationMessages.BIRTHDATE_NOT_NULL_REQUIRED)
    @BirthDate
    private LocalDate birthDate;

    @NotNull(message = ValidationMessages.PASSWORD_NOTNULL_REQUIRED)
    @Length(min = 8, max = 64, message = ValidationMessages.PASSWORD_LENGTH_CONSTRAINT)
    @Password
    private String password;
}
