package ru.yuriy.propertyrental.models.graphql.input;

import ru.yuriy.propertyrental.enums.ApartmentType;
import ru.yuriy.propertyrental.models.dto.ServiceDTO;

import java.io.Serializable;
import java.util.List;

public record ApartmentInput(
        String name,

        Double square,

        Integer roomCount,

        Double dailyPrice,

        ApartmentType type,

        String address,

        Boolean roomAvailable,

        List<ServiceDTO> services

) implements Serializable {}