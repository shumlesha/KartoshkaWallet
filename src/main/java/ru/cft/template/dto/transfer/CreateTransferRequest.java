package ru.cft.template.dto.transfer;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.validation.Comment;
import ru.cft.template.dto.validation.PaymentData;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PaymentData
public class CreateTransferRequest {
    @NotNull(message = ValidationMessages.COST_NOTNULL_REQUIRED)
    @Positive(message = ValidationMessages.COST_ONLY_POSITIVE_REQUIRED)
    @Digits(integer = 9, fraction = 0, message = ValidationMessages.COST_DIGITS_CONSTRAINT)
    private Long cost;

    private String phoneNumber;
    private UUID walletId;

    @Comment
    private String comment;
}
