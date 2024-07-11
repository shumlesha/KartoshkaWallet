package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.BILL_NOT_ALLOWED_TO_CANCEL_MESSAGE;

public class BillNotAllowedToCancelException extends RuntimeException {
    public BillNotAllowedToCancelException(UUID id) {
        super(String.format(BILL_NOT_ALLOWED_TO_CANCEL_MESSAGE, id));
    }
}
