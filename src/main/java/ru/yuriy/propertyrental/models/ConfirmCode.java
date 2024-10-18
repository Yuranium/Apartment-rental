package ru.yuriy.propertyrental.models;

public record ConfirmCode(
        Integer number1, Integer number2, Integer number3,
        Integer number4, Integer number5, Integer number6)
{
    public String toString()
    {
        return String.format("%s%s%s%s%s%s", number1, number2, number3, number4, number5, number6);
    }
}