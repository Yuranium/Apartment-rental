package ru.yuriy.propertyrental.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.graphql.input.ApartmentInput;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
uses = {UserMapper.class, ServiceMapper.class})
public interface ApartmentMapper
{
    ApartmentDTO toDTO(Apartment apartment);

    Apartment toEntity(ApartmentDTO apartmentDTO);

    Apartment toEntity(ApartmentInput apartment);

    List<ApartmentDTO> toDTOList(List<Apartment> apartments);
}