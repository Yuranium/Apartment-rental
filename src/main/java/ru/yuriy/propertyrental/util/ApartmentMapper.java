package ru.yuriy.propertyrental.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.models.entity.Apartment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
uses = UserMapper.class)
public interface ApartmentMapper
{
    ApartmentDTO toDTO(Apartment apartment);

    List<ApartmentDTO> toDTOList(List<Apartment> apartments);

    Apartment toEntity(ApartmentDTO apartmentDTO);
}