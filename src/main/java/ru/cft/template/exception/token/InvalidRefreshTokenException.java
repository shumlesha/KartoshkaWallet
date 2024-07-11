package ru.cft.template.exception.token;
import static ru.cft.template.constants.messages.ServiceMessages.INVALID_REFRESH_TOKEN_MESSAGE;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
        super(INVALID_REFRESH_TOKEN_MESSAGE);
    }
}
