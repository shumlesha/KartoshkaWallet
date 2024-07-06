package ru.cft.template.exception.transfer;
import static ru.cft.template.constants.messages.ServiceMessages.SAME_USER_TRANSFER_MESSAGE;

public class SameUserTransferException extends RuntimeException {
    public SameUserTransferException() {
        super(SAME_USER_TRANSFER_MESSAGE);
    }
}
