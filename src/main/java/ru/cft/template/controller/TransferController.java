package ru.cft.template.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.api.DefaultResponse;
import ru.cft.template.dto.transfer.CreateTransferRequest;
import ru.cft.template.dto.transfer.TransferDto;
import ru.cft.template.dto.transfer.TransferFilter;
import ru.cft.template.mapper.TransferMapper;
import ru.cft.template.models.Transfer;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.TransferService;
import ru.cft.template.util.DefaultResponseBuilder;

import java.util.UUID;

import static ru.cft.template.constants.endpoints.Endpoints.ID;
import static ru.cft.template.constants.endpoints.Endpoints.TRANSFERS_URL;
import static ru.cft.template.constants.messages.ServiceMessages.*;
import static ru.cft.template.constants.messages.SwaggerMessages.*;


@Slf4j
@RestController
@RequestMapping(TRANSFERS_URL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = TRANSFERS_TAG, description = TRANSFERS_TAG_DESCRIPTION)
public class TransferController {

    TransferService transferService;

    /**
     * Отправить личный перевод
     *
     * @param sessionUser         сессионный пользователь
     * @param createTransferRequest модель создания личного перевода
     * @return dto созданного личного перевода
     */
    @PostMapping
    @Operation(summary = TRANSFERS_SEND_SUMMARY, description = TRANSFERS_SEND_DESCRIPTION)
    public ResponseEntity<DefaultResponse<TransferDto>> sendTransfer(@AuthenticationPrincipal SessionUser sessionUser,
                                                                     @Validated @RequestBody CreateTransferRequest createTransferRequest) {
        TransferDto transfer = transferService.sendTransfer(sessionUser, createTransferRequest);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(TRANSFER_SUCCESSFULLY_SENT),
                transfer
        ));
    }

    /**
     * Получить конкретный (свой) личный перевод
     *
     * @param sessionUser сессионный пользователь
     * @param id идентификатор перевода
     * @return dto личного перевода
     */
    @GetMapping(ID)
    @Operation(summary = TRANSFERS_GET_OWN_SUMMARY, description = TRANSFERS_GET_OWN_DESCRIPTION)
    public ResponseEntity<DefaultResponse<TransferDto>> getBill(@AuthenticationPrincipal SessionUser sessionUser,
                                                                @PathVariable UUID id) {
        TransferDto transfer  = transferService.getTransfer(sessionUser, id);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(TRANSFER_SUCCESSFULLY_RETRIEVED, transfer.getId()),
                transfer
        ));
    }

    /**
     * Получить личные переводы с пагинацией и фильтрацией (связанные с собой)
     *
     * @param sessionUser    сессионный пользователь
     * @param transferFilter фильтр
     * @param pageable pageable-объект (page, size, ...)
     * @return page из transferDTO
     */
    @GetMapping
    @Operation(summary = TRANSFERS_GET_ALL_OWN_SUMMARY, description = TRANSFERS_GET_ALL_OWN_DESCRIPTION)
    public ResponseEntity<DefaultResponse<Page<TransferDto>>> getTransfers(@AuthenticationPrincipal SessionUser sessionUser,
                                                                           @ParameterObject TransferFilter transferFilter,
                                                                           @ParameterObject @PageableDefault(sort = "createdAt",
                                                                           direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(TRANSFER_LIST_SUCCESSFULLY_RETRIEVED),
                transferService.getTransfers(sessionUser, transferFilter, pageable)
        ));
    }
}
