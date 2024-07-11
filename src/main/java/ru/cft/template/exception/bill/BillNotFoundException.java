package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.BILL_NOT_FOUND_MESSAGE;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(UUID id) {
        super(String.format(BILL_NOT_FOUND_MESSAGE, id));
    }
}
