package ru.cft.template.dto.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.cft.template.constants.enums.BillStatus;
import ru.cft.template.constants.enums.OperationDirection;

import java.time.LocalDateTime;
import java.util.UUID;

public record BillFilter(
        BillStatus billStatus,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime startDate,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime endDate,
        UUID billId,
        OperationDirection direction
) {
}
