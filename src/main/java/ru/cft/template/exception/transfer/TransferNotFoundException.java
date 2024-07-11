package ru.cft.template.exception.transfer;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.TRANSFER_NOT_FOUND_MESSAGE;


public class TransferNotFoundException extends RuntimeException {
    public TransferNotFoundException(UUID transferId) {
        super(String.format(TRANSFER_NOT_FOUND_MESSAGE, transferId));
    }
}
