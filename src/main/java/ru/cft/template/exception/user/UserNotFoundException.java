package ru.cft.template.exception.user;
import java.util.UUID;

import static ru.cft.template.constants.messages.ServiceMessages.USER_NOT_FOUND_BY_EMAIL_MESSAGE;
import static ru.cft.template.constants.messages.ServiceMessages.USER_NOT_FOUND_MESSAGE;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super(String.format(USER_NOT_FOUND_MESSAGE, id));
    }

    public UserNotFoundException(String email) {
        super(String.format(USER_NOT_FOUND_BY_EMAIL_MESSAGE, email));
    }

}
