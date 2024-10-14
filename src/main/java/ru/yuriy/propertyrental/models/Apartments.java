package ru.yuriy.propertyrental.models;

import java.sql.Timestamp;
import java.util.Date;

public class Apartments
{
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private Timestamp dateRegistration;

    private Date birthday;

    private Timestamp currentDate()
    {
        return new Timestamp(System.currentTimeMillis());
    }
}