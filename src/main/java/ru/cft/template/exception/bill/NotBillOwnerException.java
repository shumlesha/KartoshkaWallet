package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.NOT_BILL_OWNER_MESSAGE;

public class NotBillOwnerException extends RuntimeException {
    public NotBillOwnerException(UUID id) {
        super(String.format(NOT_BILL_OWNER_MESSAGE, id));
    }
}
