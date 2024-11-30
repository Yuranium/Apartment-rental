package ru.yuriy.propertyrental.models.graphql.input;

import ru.yuriy.propertyrental.enums.RoleType;

import java.io.Serializable;

public record RoleInput(RoleType roleType) implements Serializable {}