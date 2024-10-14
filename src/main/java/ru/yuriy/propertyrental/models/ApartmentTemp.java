package ru.yuriy.propertyrental.models;

import lombok.Getter;
import ru.yuriy.propertyrental.enums.ApartmentType;

@Getter
public class ApartmentTemp
{
    private String address;

    private Integer countRooms;

    private Double pricePerDay;

    private ApartmentType type;
}