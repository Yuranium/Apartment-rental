package ru.yuriy.propertyrental.models.graphql.input;

import ru.yuriy.propertyrental.enums.PaymentStatus;

import java.io.Serializable;
import java.util.Date;

public record PaymentInput(Double amountPayment, Date datePayment,
        PaymentStatus status) implements Serializable {}
