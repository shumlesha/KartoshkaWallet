package ru.cft.template.dto.session;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.constants.regex.RegexPatterns;
import ru.cft.template.dto.validation.Password;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequest {
    @Email(regexp = RegexPatterns.EMAIL_PATTERN,
            message = ValidationMessages.EMAIL_VALID_REQUIRED)
    private String email;

    @NotNull(message = ValidationMessages.PASSWORD_NOTNULL_REQUIRED)
    @Length(min = 8, max = 64, message = ValidationMessages.PASSWORD_LENGTH_CONSTRAINT)
    @Password
    private String password;
}
