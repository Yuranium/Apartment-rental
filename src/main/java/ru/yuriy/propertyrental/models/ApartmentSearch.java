package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@ToString
@AllArgsConstructor
public class ApartmentSearch
{
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Integer)
    private Integer roomCount;

    @Field(type = FieldType.Double)
    private Double dailyPrice;

    @Field(type = FieldType.Text)
    private String apartmentType;

    public boolean isEmptySearch()
    {
        return name.isEmpty() && roomCount == null
                && dailyPrice == null && apartmentType.isEmpty();
    }
}