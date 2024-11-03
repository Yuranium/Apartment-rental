package ru.yuriy.propertyrental.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@ToString
@Document(indexName = "apartment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApartmentSearch
{
    @Id
    private Long id;

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