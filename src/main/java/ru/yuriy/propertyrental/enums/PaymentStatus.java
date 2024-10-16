package ru.yuriy.propertyrental.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus
{
    NOT_PAID("Не оплачен"), PAID("Оплачен"), OVERDUE("Просрочен");

    private final String status;
}