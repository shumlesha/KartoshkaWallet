package ru.cft.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.wallet.HesoyamDTO;
import ru.cft.template.dto.wallet.WalletDTO;
import ru.cft.template.mapper.WalletMapper;
import ru.cft.template.models.Wallet;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.WalletService;
import ru.cft.template.util.DefaultResponseBuilder;

import static ru.cft.template.constants.endpoints.Endpoints.HESOYAM;
import static ru.cft.template.constants.endpoints.Endpoints.WALLETS_URL;
import static ru.cft.template.constants.messages.ServiceMessages.*;
import static ru.cft.template.constants.messages.SwaggerMessages.*;


@Slf4j
@RestController
@RequestMapping(WALLETS_URL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = WALLETS_TAG)
public class WalletController {

    WalletService walletService;
    WalletMapper walletMapper;

    /**
     * Позволяет получить свой кошелек
     *
     * @param sessionUser AuthenticationPrincipal (Текущий пользователь)
     * @return dto кошелька
     */
    @GetMapping
    @Operation(summary = WALLETS_GET_SUMMARY, description = WALLETS_GET_DESCRIPTION)
    public ResponseEntity<DefaultResponse<WalletDTO>> getWallet(@AuthenticationPrincipal SessionUser sessionUser) {

        Wallet wallet = walletService.getWallet(sessionUser);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
            String.format(WALLET_SUCCESSFULLY_RETRIEVED, wallet.getId()),
            walletMapper.toDTO(wallet)
        ));
    }


    /**
     * Hesoyam
     *
     * @param sessionUser AuthenticationPrincipal (Текущий пользователь)
     * @return HesoyamDTO - содержит boolean
     */
    @PostMapping(HESOYAM)
    @Operation(summary = WALLETS_HESOYAM_SUMMARY, description = WALLETS_HESOYAM_DESCRIPTION)
    public ResponseEntity<DefaultResponse<HesoyamDTO>> hesoyam(@AuthenticationPrincipal SessionUser sessionUser) {
        boolean lucky = walletService.hesoyam(sessionUser);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(lucky ? WALLET_LUCKY_MESSAGE : WALLET_UNLUCKY_MESSAGE),
                new HesoyamDTO(lucky)
        ));
    }

}