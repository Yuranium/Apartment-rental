package ru.yuriy.propertyrental.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yuriy.propertyrental.models.dto.UserDTO;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.models.graphql.input.UserInput;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper
{
    UserDTO toDTO(User user);

    User toEntity(UserInput user);

    List<UserDTO> listUserToDTO(List<User> users);
}