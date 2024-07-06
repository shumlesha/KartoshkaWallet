package ru.cft.template.exception.user;
import static ru.cft.template.constants.messages.ServiceMessages.USER_ALREADY_EXISTS_MESSAGE;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String field, String credential) {
        super(String.format(USER_ALREADY_EXISTS_MESSAGE, field, credential));
    }
}
