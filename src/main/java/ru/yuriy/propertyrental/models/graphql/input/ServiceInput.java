package ru.yuriy.propertyrental.models.graphql.input;

import java.io.Serializable;

public record ServiceInput(String name, String description,
                           Double servicePrice) implements Serializable {}