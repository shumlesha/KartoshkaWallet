package ru.cft.template.service;
import ru.cft.template.models.Wallet;
import ru.cft.template.security.SessionUser;

public interface WalletService {
    Wallet getWallet(SessionUser sessionUser);

    boolean hesoyam(SessionUser sessionUser);
}
