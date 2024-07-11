package ru.cft.template.constants.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BillStatus {
    PAID("Оплачен"),
    UNPAID("Неоплачен"),
    CANCELLED("Отменен");

    private final String value;
}
