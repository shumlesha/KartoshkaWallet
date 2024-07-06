package ru.cft.template.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.cft.template.dto.transfer.CreateTransferModel;
import ru.cft.template.dto.transfer.TransferFilter;
import ru.cft.template.models.Transfer;
import ru.cft.template.security.SessionUser;

import java.util.UUID;

public interface TransferService {

    Transfer sendTransfer(SessionUser sessionUser, CreateTransferModel createTransferModel);

    Transfer getTransfer(SessionUser sessionUser, UUID id);

    Page<Transfer> getTransfers(SessionUser sessionUser, TransferFilter transferFilter, Pageable pageable);
}
