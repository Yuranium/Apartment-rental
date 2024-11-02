package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ApartmentSearch
{
    private String name;

    private Integer countRooms;

    private Double dailyPrice;

    private String apartmentType;

    public boolean isEmptySearch()
    {
        return name.isEmpty() && countRooms == null
                && dailyPrice == null && apartmentType.isEmpty();
    }
}