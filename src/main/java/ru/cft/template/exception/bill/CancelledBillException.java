package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.CANCELLED_BILL_MESSAGE;

public class CancelledBillException extends RuntimeException {
    public CancelledBillException(UUID id) {
        super(String.format(CANCELLED_BILL_MESSAGE, id));
    }
}
