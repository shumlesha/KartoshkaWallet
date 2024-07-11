package ru.cft.template.constants.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferStatus {
    PAID("Оплачен"),
    UNPAID("Неоплачен");

    private final String value;
}
