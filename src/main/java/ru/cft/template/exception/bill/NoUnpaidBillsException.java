package ru.cft.template.exception.bill;
import static ru.cft.template.constants.messages.ServiceMessages.NO_UNPAID_BILLS_MESSAGE;

public class NoUnpaidBillsException extends RuntimeException {
    public NoUnpaidBillsException() {
        super(NO_UNPAID_BILLS_MESSAGE);
    }
}
