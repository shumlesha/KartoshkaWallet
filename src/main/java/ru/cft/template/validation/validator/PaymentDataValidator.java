package ru.cft.template.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.constants.RegexPatterns;
import ru.cft.template.dto.transfer.CreateTransferRequest;
import ru.cft.template.validation.PaymentData;

import java.util.regex.Pattern;

public class PaymentDataValidator implements ConstraintValidator<PaymentData, CreateTransferRequest> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(RegexPatterns.PHONE_NUMBER_PATTERN);

    @Override
    public boolean isValid(CreateTransferRequest data, ConstraintValidatorContext context) {

        if (data.getPhoneNumber() != null && data.getWalletId() != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidationMessages.ILLEGAL_PAYMENT_DATA)
                    .addConstraintViolation();
            return false;
        }

        if (data.getWalletId() != null) {
            return true;
        }

        return isValidPhoneNumber(data.getPhoneNumber());
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}
