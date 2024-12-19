package ru.yuriy.propertyrental.models.dto;

import java.io.Serializable;
import ru.yuriy.propertyrental.models.entity.Image;

/**
 * DTO for {@link Image}
 */

public record ImageDTO(
        Long id,
        String name,
        String contentType,
        Long size,
        byte[] imageBytes
) implements Serializable {}