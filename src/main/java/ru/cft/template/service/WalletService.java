package ru.cft.template.service;
import ru.cft.template.dto.wallet.HesoyamDto;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.security.SessionUser;

public interface WalletService {
    WalletDto getWallet(SessionUser sessionUser);

    HesoyamDto hesoyam(SessionUser sessionUser);
}
