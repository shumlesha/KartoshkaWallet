package ru.cft.template.exception.wallet;
import static ru.cft.template.constants.messages.ServiceMessages.NOT_ENOUGH_MONEY_MESSAGE;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(Long diff) {
        super(String.format(NOT_ENOUGH_MONEY_MESSAGE, diff));
    }
}
