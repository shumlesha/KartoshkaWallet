package ru.cft.template.exception.bill;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.NOT_BILL_RECIPIENT_MESSAGE;


public class NotBillRecipientException extends RuntimeException {
    public NotBillRecipientException(UUID id) {
        super(String.format(NOT_BILL_RECIPIENT_MESSAGE, id));
    }
}
