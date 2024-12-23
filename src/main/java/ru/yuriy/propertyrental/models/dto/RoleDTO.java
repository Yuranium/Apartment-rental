package ru.yuriy.propertyrental.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.yuriy.propertyrental.models.entity.Role;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link Role}
 */

public record RoleDTO(
        String roleType,

        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Timestamp assignmentDate

) implements Serializable {}