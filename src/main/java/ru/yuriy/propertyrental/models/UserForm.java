package ru.yuriy.propertyrental.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm
{
    @Size(min = 3, max = 50, message = "Имя слишком короткое или слишком длинное!")
    @NotEmpty(message = "Поле пустое!")
    @NotNull(message = "Обязательное поле!")
    private String name;

    @Size(min = 3, max = 50, message = "Фамилия слишком короткая или слишком длинная!")
    @NotEmpty(message = "Поле пустое!")
    private String lastName;

    @Email
    @NotEmpty(message = "Поле пустое!")
    @NotNull(message = "Обязательное поле!")
    private String email;

    @Size(min = 10, max = 15, message = "Некорректный телефон!")
    @NotEmpty(message = "Поле пустое!")
    @NotNull(message = "Обязательное поле!")
    private String phone;

    @Size(min = 7, max = 50, message = "Пароль слишком короткий или слишком длинный!")
    @NotEmpty(message = "Поле пустое!")
    @NotNull(message = "Обязательное поле!")
    private String password;

    private String replayPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public Boolean equalsPassword()
    {
        return password.equals(replayPassword);
    }
}