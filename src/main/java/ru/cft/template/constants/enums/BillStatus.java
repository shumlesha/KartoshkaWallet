package ru.cft.template.constants.enums;
import lombok.Getter;

@Getter
public enum BillStatus {
    PAID("Оплачен"),
    UNPAID("Неоплачен"),
    CANCELLED("Отменен");

    private final String prettyName;

    BillStatus(String prettyName) {
        this.prettyName = prettyName;
    }
}
