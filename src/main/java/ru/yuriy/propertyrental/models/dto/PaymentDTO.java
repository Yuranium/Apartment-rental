package ru.yuriy.propertyrental.models.dto;

import ru.yuriy.propertyrental.enums.PaymentStatus;
import ru.yuriy.propertyrental.models.entity.Payment;

import java.util.Date;

/**
 * DTO for {@link Payment}
 */

public record PaymentDTO(Double amountPayment, Date datePayment,
                         PaymentStatus status) {}