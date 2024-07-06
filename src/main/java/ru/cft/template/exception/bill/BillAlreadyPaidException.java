package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.BILL_ALREADY_PAID_MESSAGE;


public class BillAlreadyPaidException extends RuntimeException {
    public BillAlreadyPaidException(UUID id) {
        super(String.format(BILL_ALREADY_PAID_MESSAGE, id));
    }
}
