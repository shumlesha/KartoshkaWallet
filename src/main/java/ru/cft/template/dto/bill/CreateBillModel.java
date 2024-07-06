package ru.cft.template.dto.bill;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.validation.Comment;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillModel {

    @NotNull(message = ValidationMessages.COST_NOTNULL_REQUIRED)
    @Positive(message = ValidationMessages.COST_ONLY_POSITIVE_REQUIRED)
    @Digits(integer = 9, fraction = 0, message = ValidationMessages.COST_DIGITS_CONSTRAINT)
    private Long cost;


    @NotNull(message = ValidationMessages.RECIPIENT_NOTNULL_REQUIRED)
    private UUID recipientId;

    @Comment
    private String comment;

}
