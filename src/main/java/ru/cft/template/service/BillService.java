package ru.cft.template.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.cft.template.dto.bill.BillDto;
import ru.cft.template.dto.bill.BillFilter;
import ru.cft.template.dto.bill.CreateBillRequest;
import ru.cft.template.dto.bill.DebtDto;
import ru.cft.template.security.SessionUser;

import java.util.UUID;

public interface BillService {
    BillDto createBill(SessionUser user, CreateBillRequest createBillRequest);

    BillDto cancelBill(SessionUser sessionUser, UUID id);

    BillDto payBill(SessionUser sessionUser, UUID id);

    BillDto getBill(SessionUser sessionUser, UUID id);

    Page<BillDto> getBills(SessionUser sessionUser, BillFilter billFilter, Pageable pageable);

    BillDto getOldestUnpaidBill(SessionUser sessionUser);

    DebtDto getTotalDebt(SessionUser sessionUser);
}