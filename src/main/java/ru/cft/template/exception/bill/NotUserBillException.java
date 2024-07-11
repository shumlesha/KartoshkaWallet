package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.NOT_USER_BILL_MESSAGE;

public class NotUserBillException extends RuntimeException {
    public NotUserBillException(UUID id) {
        super(String.format(NOT_USER_BILL_MESSAGE, id));
    }
}
