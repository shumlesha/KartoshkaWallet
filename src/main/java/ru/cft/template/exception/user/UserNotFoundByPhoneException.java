package ru.cft.template.exception.user;
import static ru.cft.template.constants.messages.ServiceMessages.USER_NOT_FOUND_BY_PHONE_MESSAGE;

public class UserNotFoundByPhoneException extends RuntimeException {
    public UserNotFoundByPhoneException(String phoneNumber) {
        super(String.format(USER_NOT_FOUND_BY_PHONE_MESSAGE, phoneNumber));
    }
}
