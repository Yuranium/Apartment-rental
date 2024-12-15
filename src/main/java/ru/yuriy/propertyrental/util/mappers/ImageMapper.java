package ru.yuriy.propertyrental.util.mappers;

import org.mapstruct.*;
import ru.yuriy.propertyrental.models.dto.ImageDTO;
import ru.yuriy.propertyrental.models.entity.Image;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper
{
    ImageDTO toDTO(Image image);

    Image toEntity(ImageDTO imageDTO);
}