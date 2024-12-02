package ru.yuriy.propertyrental.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmCode
{
    @Min(value = 0, message = "Некорректное число!")
    @Max(value = 9, message = "Некорректное число!")
    private Integer number1;

    @Min(value = 0, message = "Некорректное число!")
    @Max(value = 9, message = "Некорректное число!")
    private Integer number2;

    @Min(value = 0, message = "Некорректное число!")
    @Max(value = 9, message = "Некорректное число!")
    private Integer number3;

    @Min(value = 0, message = "Некорректное число!")
    @Max(value = 9, message = "Некорректное число!")
    private Integer number4;

    @Min(value = 0, message = "Некорректное число!")
    @Max(value = 9, message = "Некорректное число!")
    private Integer number5;

    @Min(value = 0, message = "Некорректное число!")
    @Max(value = 9, message = "Некорректное число!")

    private Integer number6;
    public String toString()
    {
        return String.format("%s%s%s%s%s%s", number1, number2, number3, number4, number5, number6);
    }
}