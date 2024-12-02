package ru.yuriy.propertyrental.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.yuriy.propertyrental.enums.ApartmentType;
import ru.yuriy.propertyrental.models.entity.Service;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentForm
{
    @Size(min = 5, max = 50, message = "Некорректное имя апартамента!")
    @NotNull(message = "Обязательное поле!")
    @NotBlank(message = "Обязательное поле!")
    private String name;

    @Min(value = 1, message = "Некорректное значение площади!")
    @NotNull(message = "Обязательное поле!")
    private Double square;

    @Min(value = 1, message = "Количество комнат должно быть больше 0!")
    @NotNull(message = "Обязательное поле!")
    @NotBlank(message = "Обязательное поле!")
    private Integer roomCount;

    @Min(value = 0, message = "Некорректная цена!")
    @NotNull(message = "Обязательное поле!")
    private Double dailyPrice;

    @NotNull(message = "Обязательное поле!")
    private ApartmentType type;

    @Size(min = 5, max = 100, message = "Адрес слишком короткий или слишком длинный!")
    @NotNull(message = "Обязательное поле!")
    @NotBlank(message = "Обязательное поле!")
    private String address;

    private List<MultipartFile> images;

    private List<Service> services;
}