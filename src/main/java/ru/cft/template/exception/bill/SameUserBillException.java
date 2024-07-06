package ru.cft.template.exception.bill;
import static ru.cft.template.constants.messages.ServiceMessages.SAME_USER_BILL_MESSAGE;

public class SameUserBillException extends RuntimeException {
    public SameUserBillException() {
        super(SAME_USER_BILL_MESSAGE);
    }
}
