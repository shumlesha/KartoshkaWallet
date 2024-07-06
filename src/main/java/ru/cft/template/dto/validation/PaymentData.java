package ru.cft.template.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.cft.template.constants.messages.ValidationMessages;
import ru.cft.template.dto.validation.validator.PaymentDataValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PaymentDataValidator.class})
public @interface PaymentData {
    String message() default ValidationMessages.PAYMENT_DATA_INCORRECT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
