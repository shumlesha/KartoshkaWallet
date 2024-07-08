package ru.cft.template.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.cft.template.dto.transfer.CreateTransferRequest;
import ru.cft.template.dto.transfer.TransferDto;
import ru.cft.template.dto.transfer.TransferFilter;
import ru.cft.template.models.Transfer;
import ru.cft.template.security.SessionUser;

import java.util.UUID;

public interface TransferService {

    TransferDto sendTransfer(SessionUser sessionUser, CreateTransferRequest createTransferRequest);

    TransferDto getTransfer(SessionUser sessionUser, UUID id);

    Page<TransferDto> getTransfers(SessionUser sessionUser, TransferFilter transferFilter, Pageable pageable);
}
