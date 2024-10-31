package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ru.yuriy.propertyrental.enums.ApartmentType;

@Document(indexName = "apartment")
public class ApartmentES
{
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Double)
    private Double square;

    @Field(type = FieldType.Integer)
    private Integer roomCount;

    @Field(type = FieldType.Double)
    private Double dailyPrice;

    private ApartmentType type;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Boolean)
    private Boolean roomAvailable;
}