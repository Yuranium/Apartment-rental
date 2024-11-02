package ru.yuriy.propertyrental.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.MapKeyEnumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ru.yuriy.propertyrental.enums.ApartmentType;

@ToString
@Getter
@Setter
@Document(indexName = "apartment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApartmentES
{
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private Double square;

    @Field(type = FieldType.Keyword)
    private Integer roomCount;

    @Field(type = FieldType.Double)
    private Double dailyPrice;

    @Field(type = FieldType.Keyword)
    private ApartmentType type;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Boolean)
    private Boolean roomAvailable;
}