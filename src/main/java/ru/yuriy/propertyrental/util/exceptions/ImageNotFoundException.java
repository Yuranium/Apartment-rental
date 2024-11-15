package ru.yuriy.propertyrental.util.exceptions;

public class ImageNotFoundException extends RuntimeException
{
    public ImageNotFoundException(String message)
    {
        super(message);
    }
}