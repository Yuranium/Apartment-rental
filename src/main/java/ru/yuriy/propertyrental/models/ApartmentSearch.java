package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.yuriy.propertyrental.enums.ApartmentType;

@Getter
@ToString
@AllArgsConstructor
public class ApartmentSearch
{
    private String address;

    private Integer countRooms;

    private Double dailyPrice;

    private String apartmentType;
}