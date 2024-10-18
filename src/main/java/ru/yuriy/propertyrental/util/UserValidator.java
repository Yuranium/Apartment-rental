package ru.yuriy.propertyrental.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.perositories.UserRepository;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator
{
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return UserForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        UserForm user = (UserForm) target;
        if (userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone()).isPresent())
        {
            errors.rejectValue("email", "", "Данный Email уже занят!");
            errors.rejectValue("phone", "", "Данный телефон уже есть в базе!");
        }
        if (!user.equalsPassword())
            errors.rejectValue("replayPassword", "", "Пароли не совпадают!");
        if (new Date(System.currentTimeMillis()).before(user.getBirthday()))
            errors.rejectValue("birthday", "", "Введённая дата больше текущей!");
    }
}