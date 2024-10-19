package ru.yuriy.propertyrental.models;

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
    private Integer number1;

    private Integer number2;

    private Integer number3;

    private Integer number4;

    private Integer number5;

    private Integer number6;
    public String toString()
    {
        return String.format("%s%s%s%s%s%s", number1, number2, number3, number4, number5, number6);
    }
}