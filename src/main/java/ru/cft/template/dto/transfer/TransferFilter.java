package ru.cft.template.dto.transfer;

import ru.cft.template.constants.enums.OperationDirection;
import ru.cft.template.constants.enums.TransferStatus;

import java.util.UUID;

public record TransferFilter(
        OperationDirection direction,
        TransferStatus transferStatus,
        UUID recipientId
) {
}
