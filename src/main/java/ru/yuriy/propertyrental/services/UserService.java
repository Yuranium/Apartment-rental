package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.perositories.UserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    public Boolean saveNewUser(UserForm userForm, BindingResult result)
    {
        if (!userForm.equalsPassword())
        {
            result.rejectValue("confirmPassword", "error.userForm", "Пароли не совпадают");
            return Boolean.FALSE;
        }
        else
        {
            saveUser(userForm);
            return Boolean.TRUE;
        }
    }

    private void saveUser(UserForm userForm)
    {
        User user = new User();
        user.setName(userForm.getName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setPhone(userForm.getPhone());
        user.setPassword(userForm.getPassword());
        user.setBirthday(new Date(userForm.getBirthday()));
        user.setActive(Boolean.FALSE);
        // userRepository.save(user);
    }
}