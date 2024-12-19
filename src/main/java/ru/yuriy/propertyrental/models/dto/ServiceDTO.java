package ru.yuriy.propertyrental.models.dto;

import ru.yuriy.propertyrental.models.entity.Service;

import java.io.Serializable;

/**
 * DTO for {@link Service}
 */

public record ServiceDTO(
        String name,
        String description,
        Double servicePrice
) implements Serializable {}