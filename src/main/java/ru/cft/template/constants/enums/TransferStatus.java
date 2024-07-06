package ru.cft.template.constants.enums;

import lombok.Getter;

@Getter
public enum TransferStatus {
    PAID("Оплачен"),
    UNPAID("Неоплачен");

    private final String prettyName;

    TransferStatus(String prettyName) {
        this.prettyName = prettyName;
    }
}
