package ru.cft.template.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.dto.wallet.HesoyamDto;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.mapper.WalletMapper;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.WalletService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final Random random = new Random();

    @Override
    @Transactional(readOnly = true)
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
