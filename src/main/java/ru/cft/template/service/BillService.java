package ru.cft.template.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.cft.template.dto.bill.BillDto;
import ru.cft.template.dto.bill.BillFilter;
import ru.cft.template.dto.bill.CreateBillRequest;
import ru.cft.template.models.Bill;
import ru.cft.template.security.SessionUser;

import java.util.UUID;

public interface BillService {
    Bill createBill(SessionUser user, CreateBillRequest createBillRequest);

    Bill cancelBill(SessionUser sessionUser, UUID id);

    Bill payBill(SessionUser sessionUser, UUID id);

    Bill getBill(SessionUser sessionUser, UUID id);

    Page<BillDto> getBills(SessionUser sessionUser, BillFilter billFilter, Pageable pageable);

    Bill getOldestUnpaidBill(SessionUser sessionUser);

    Long getTotalDebt(SessionUser sessionUser);
}