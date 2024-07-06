package ru.cft.template.exception.bill;
import static ru.cft.template.constants.messages.ServiceMessages.BILL_FILTER_CONFLICT_MESSAGE;

public class BillFilterConflictException extends RuntimeException {
    public BillFilterConflictException() {
        super(BILL_FILTER_CONFLICT_MESSAGE);
    }
}
