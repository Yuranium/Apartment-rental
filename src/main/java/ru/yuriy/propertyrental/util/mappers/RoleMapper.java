package ru.yuriy.propertyrental.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yuriy.propertyrental.models.dto.RoleDTO;
import ru.yuriy.propertyrental.models.entity.Role;
import ru.yuriy.propertyrental.models.graphql.input.RoleInput;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper
{
    RoleDTO toDTO(Role role);

    Role toEntity(RoleInput role);

    List<RoleDTO> listToDTO(List<Role> roles);
}