package ru.cft.template.exception.wallet;
import java.util.UUID;
import static ru.cft.template.constants.messages.ServiceMessages.WALLET_NOT_FOUND_MESSAGE;


public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID id) {
        super(String.format(WALLET_NOT_FOUND_MESSAGE, id));
    }
}
