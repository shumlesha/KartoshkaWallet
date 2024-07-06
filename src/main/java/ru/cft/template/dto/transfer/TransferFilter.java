package ru.cft.template.dto.transfer;

import ru.cft.template.constants.enums.TransferDirection;
import ru.cft.template.constants.enums.TransferStatus;

import java.util.UUID;

public record TransferFilter(
        TransferDirection direction,
        TransferStatus transferStatus,
        UUID recipientId
) {
}
