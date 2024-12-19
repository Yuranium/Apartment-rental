package ru.yuriy.propertyrental.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.yuriy.propertyrental.enums.PaymentStatus;
import ru.yuriy.propertyrental.models.entity.Payment;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Payment}
 */

public record PaymentDTO(
        Double amountPayment,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd", timezone = "UTC")
        Date datePayment,
        PaymentStatus status
) implements Serializable {}