package ru.yuriy.propertyrental.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApartmentType
{
    FLAT("Квартира"), FLAT_STUDIO("Квартира-студия"),
    HOTEL("Отель"), HOUSE("Дом"), OFFICE("Офис");

    private final String type;
}