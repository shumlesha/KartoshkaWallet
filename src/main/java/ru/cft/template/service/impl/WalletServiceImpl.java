package ru.cft.template.service.impl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.dto.wallet.HesoyamDto;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.mapper.WalletMapper;
import ru.cft.template.models.User;
import ru.cft.template.models.Wallet;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.WalletService;

import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;
    WalletMapper walletMapper;
    Random random = new Random();

    @Override
    public WalletDto getWallet(SessionUser sessionUser) {
        User user = sessionUser.getSession().getUser();

        return walletMapper.toDTO(walletRepository.findByOwner(user));
    }

    @Override
    @Transactional
    public HesoyamDto hesoyam(SessionUser sessionUser) {
        boolean lucky = random.nextDouble() < 0.25;

        Wallet wallet = sessionUser.getSession().getUser().getWallet();
        Long balance = wallet.getBalance();

        if (lucky) {
            wallet.setBalance(balance + 10);
            walletRepository.save(wallet);
        }

        return new HesoyamDto(lucky);
    }
}
