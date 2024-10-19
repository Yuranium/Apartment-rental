package ru.yuriy.propertyrental.util;

public class RoleNotFoundException extends RuntimeException
{
    public RoleNotFoundException(String message)
    {
        super(message);
    }
}