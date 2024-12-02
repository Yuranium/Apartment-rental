package ru.yuriy.propertyrental.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yuriy.propertyrental.models.dto.ServiceDTO;
import ru.yuriy.propertyrental.models.entity.Service;
import ru.yuriy.propertyrental.models.graphql.input.ServiceInput;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper
{
    ServiceDTO toDTO(Service service);

    Service toEntity(ServiceInput service);

    List<ServiceDTO> toDTOList(List<Service> services);
}