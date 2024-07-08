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
import ru.cft.template.dto.bill.BillDto;
import ru.cft.template.dto.bill.BillFilter;
import ru.cft.template.dto.bill.CreateBillRequest;
import ru.cft.template.dto.bill.DebtDto;
import ru.cft.template.mapper.BillMapper;
import ru.cft.template.models.Bill;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.BillService;
import ru.cft.template.util.DefaultResponseBuilder;

import java.util.UUID;

import static ru.cft.template.constants.endpoints.Endpoints.*;
import static ru.cft.template.constants.messages.ServiceMessages.*;
import static ru.cft.template.constants.messages.SwaggerMessages.*;


@Slf4j
@RestController
@RequestMapping(BILLS_URL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = BILLS_TAG, description = BILLS_TAG_DESCRIPTION)
public class BillController {

    BillService billService;

    /**
     * Создать счет на оплату
     *
     * @param sessionUser     сессионный пользователь
     * @param createBillRequest модель создания счета на оплату
     * @return dto счета на оплату
     */
    @PostMapping
    @Operation(summary = BILLS_CREATE_SUMMARY, description = BILLS_CREATE_DESCRIPTION)
    public ResponseEntity<DefaultResponse<BillDto>> createBill(@AuthenticationPrincipal SessionUser sessionUser,
                                                               @Validated @RequestBody CreateBillRequest createBillRequest) {
        BillDto createdBill = billService.createBill(sessionUser, createBillRequest);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(BILL_SUCCESSFULLY_CREATED, createdBill.getId()),
                createdBill
        ));
    }


    /**
     * Отмена счета на оплату отправителем
     *
     * @param sessionUser сессионный пользователь
     * @param id идентификатор счета на оплату
     * @return dto отмененного счета на оплату
     */
    @PostMapping(BILLS_CANCEL)
    @Operation(summary = BILLS_CANCEL_SUMMARY, description = BILLS_CANCEL_DESCRIPTION)
    public ResponseEntity<DefaultResponse<BillDto>> cancelBill(@AuthenticationPrincipal SessionUser sessionUser,
                                                               @PathVariable UUID id) {
        BillDto cancelledBill = billService.cancelBill(sessionUser, id);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(BILL_SUCCESSFULLY_CANCELED, id),
                cancelledBill
        ));
    }

    /**
     * Оплатить счет на оплату получателем
     *
     * @param sessionUser сесионный пользователь
     * @param id идентификатор счета на оплату
     * @return dto оплаченного счета на оплату
     */
    @PostMapping(BILLS_PAY)
    @Operation(summary = BILLS_PAY_SUMMARY, description = BILLS_PAY_DESCRIPTION)
    public ResponseEntity<DefaultResponse<BillDto>> payBill(@AuthenticationPrincipal SessionUser sessionUser,
                                                            @PathVariable UUID id) {
        BillDto bill = billService.payBill(sessionUser, id);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(BILL_SUCCESSFULLY_PAYED, id),
                bill
        ));
    }


    /**
     * Получить конкретный счет на оплату
     *
     * @param sessionUser сессионный пользователь
     * @param id идентификатор счета на оплату
     * @return dto счета на оплату
     */
    @GetMapping(ID)
    @Operation(summary = BILLS_GET_OWN_SUMMARY, description = BILLS_GET_OWN_DESCRIPTION)
    public ResponseEntity<DefaultResponse<BillDto>> getBill(@AuthenticationPrincipal SessionUser sessionUser,
                                                            @PathVariable UUID id) {
        BillDto bill = billService.getBill(sessionUser, id);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(BILL_SUCCESSFULLY_RETRIEVED, bill.getId()),
                bill
        ));
    }

    /**
     * Получить связанные с собой счета с пагинацией и фильтрацией
     *
     * @param sessionUser сессионный пользователь
     * @param billFilter  совокупность параметров фильтрации
     * @param pageable    pageable-объект (page, size, ...)
     * @return page из billDTO
     */
    @GetMapping
    @Operation(summary = BILLS_GET_ALL_OWN_SUMMARY, description = BILLS_GET_ALL_OWN_DESCRIPTION)
    public ResponseEntity<DefaultResponse<Page<BillDto>>> getBills(@AuthenticationPrincipal SessionUser sessionUser,
                                                                   @ParameterObject BillFilter billFilter,
                                                                   @ParameterObject @PageableDefault(sort = "createdAt",
                                                                           direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(BILL_LIST_SUCCESSFULLY_RETRIEVED),
                billService.getBills(sessionUser, billFilter, pageable)
        ));
    }

    /**
     * Получить наиболее давний неоплаченный счет
     *
     * @param sessionUser сессионный пользователь
     * @return dto наиболее давнего неоплаченного счета
     */
    @GetMapping(BILLS_OLDEST_UNPAID)
    @Operation(summary = BILLS_GET_OLDEST_UNPAID_SUMMARY, description = BILLS_GET_OLDEST_UNPAID_DESCRIPTION)
    public ResponseEntity<DefaultResponse<BillDto>> getOldestUnpaidBill(@AuthenticationPrincipal SessionUser sessionUser) {
        BillDto bill = billService.getOldestUnpaidBill(sessionUser);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(BILL_SUCCESSFULLY_RETRIEVED, bill.getId()),
                bill
        ));
    }

    /**
     * Получить суммарную свою задолженность
     *
     * @param sessionUser сессионный пользователь
     * @return суммарный долг (long)
     */
    @GetMapping(BILLS_TOTAL_DEBT)
    @Operation(summary = BILLS_GET_TOTAL_DEBT_SUMMARY, description = BILLS_GET_TOTAL_DEBT_DESCRIPTION)
    public ResponseEntity<DefaultResponse<DebtDto>> getTotalDebt(@AuthenticationPrincipal SessionUser sessionUser) {
        DebtDto debt = billService.getTotalDebt(sessionUser);

        return ResponseEntity.ok(DefaultResponseBuilder.success(
                String.format(DEBT_SUCCESSFULLY_RETRIEVED, debt.getDebt()),
                debt
        ));
    }
}