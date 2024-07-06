package ru.cft.template.exception.transfer;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.NOT_USER_TRANSFER_MESSAGE;


public class NotUserTransferException extends RuntimeException {
    public NotUserTransferException(UUID transferId) {
        super(String.format(NOT_USER_TRANSFER_MESSAGE, transferId));
    }
}
