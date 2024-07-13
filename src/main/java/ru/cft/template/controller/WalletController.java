package ru.cft.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.wallet.HesoyamDto;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.WalletService;
import ru.cft.template.util.ResponseBuilder;

import static ru.cft.template.constants.Endpoints.HESOYAM;
import static ru.cft.template.constants.Endpoints.WALLETS_URL;
import static ru.cft.template.constants.messages.ServiceMessages.*;
import static ru.cft.template.constants.messages.SwaggerMessages.*;


@Slf4j
@RestController
@RequestMapping(WALLETS_URL)
@RequiredArgsConstructor
@Tag(name = WALLETS_TAG)
public class WalletController {

    private final WalletService walletService;

    /**
     * Позволяет получить свой кошелек
     *
     * @param sessionUser AuthenticationPrincipal (Текущий пользователь)
     * @return dto кошелька
     */
    @GetMapping
    @Operation(summary = WALLETS_GET_SUMMARY, description = WALLETS_GET_DESCRIPTION)
    public ResponseEntity<DefaultResponse<WalletDto>> getWallet(@AuthenticationPrincipal SessionUser sessionUser) {

        WalletDto wallet = walletService.getWallet(sessionUser);

        return ResponseEntity.ok(ResponseBuilder.success(
                String.format(WALLET_SUCCESSFULLY_RETRIEVED, wallet.getId()),
                wallet
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
    public ResponseEntity<DefaultResponse<HesoyamDto>> hesoyam(@AuthenticationPrincipal SessionUser sessionUser) {
        HesoyamDto lucky = walletService.hesoyam(sessionUser);

        return ResponseEntity.ok(ResponseBuilder.success(
                String.format(lucky.isWon() ? WALLET_LUCKY_MESSAGE : WALLET_UNLUCKY_MESSAGE),
                lucky
        ));
    }

}