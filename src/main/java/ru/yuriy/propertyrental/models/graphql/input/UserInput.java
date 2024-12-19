package ru.yuriy.propertyrental.models.graphql.input;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity for graphql
 */

public record UserInput(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        String password,
        Date birthday
) implements Serializable {}