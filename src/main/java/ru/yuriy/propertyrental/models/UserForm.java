package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserForm
{
    private String name;

    private String lastName;

    private String email;

    private String phone;

    private String password;

    private String replayPassword;

    private String birthday;

    public Boolean equalsPassword()
    {
        return password.equals(replayPassword);
    }
}