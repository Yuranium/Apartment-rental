package ru.yuriy.propertyrental.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yuriy.propertyrental.models.dto.PaymentDTO;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.graphql.input.PaymentInput;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper
{
    PaymentDTO toDTO(Payment payment);

    Payment toEntity(PaymentInput payment);

    Set<PaymentDTO> listToDTO(Set<Payment> payments);
}