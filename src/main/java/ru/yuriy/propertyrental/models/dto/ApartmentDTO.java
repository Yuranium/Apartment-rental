package ru.yuriy.propertyrental.models.dto;

import ru.yuriy.propertyrental.enums.ApartmentType;
import ru.yuriy.propertyrental.models.entity.Apartment;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Apartment}
 */

public record ApartmentDTO(
        String name,
        Double square,
        Integer roomCount,
        Double dailyPrice,
        ApartmentType type,
        String address,
        Boolean roomAvailable,
        UserDTO user,
        List<ServiceDTO> services
) implements Serializable {}