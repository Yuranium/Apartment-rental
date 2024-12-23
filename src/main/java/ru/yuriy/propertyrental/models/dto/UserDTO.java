package ru.yuriy.propertyrental.models.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import ru.yuriy.propertyrental.models.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link User}
 */

public record UserDTO(
        Long id,

        String name,

        String email,

        String phone,

        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "UTC")
        Timestamp dateRegistration,

        Boolean active,

        List<RoleDTO> roles

) implements Serializable {}