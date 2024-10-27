package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.RoleRepository;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.RoleNotFoundException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<User> userList()
    {
        return userRepository.findAll();
    }

    @SneakyThrows
    @Transactional(rollbackFor = {RuntimeException.class})
    public void saveUser(UserForm userForm)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setName(userForm.getName());
        user.setLastName(user.getLastName().isBlank() ? null : userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setPhone(user.getPhone().isBlank() ? null : userForm.getPhone());
        user.setPassword(userForm.getPassword());
        user.setBirthday(userForm.getBirthday().isBlank() ? null :
                dateFormat.parse(userForm.getBirthday()));
        user.setActive(Boolean.FALSE);
        user.setRoles(List.of(roleRepository.findById(2L).orElseThrow(() -> new RoleNotFoundException(
                "ОШИБКА: Для данного пользователя роль не была установлена"))));
        userRepository.save(user);
    }

    @Transactional
    public boolean updateUser(UserForm userForm)
    {
        Optional<User> userOpt = userRepository.findByPhoneAndEmail(userForm.getPhone(), userForm.getEmail());
        if (userOpt.isEmpty())
            return false;
        else
        {
            User user1 = userOpt.get();
            user1.setName(userForm.getName());
            user1.setLastName(userForm.getLastName());
            user1.setEmail(userForm.getEmail());
            user1.setPhone(userForm.getPhone());
            user1.setPassword(userForm.getPassword());
            return true;
        }
    }

    @Transactional
    public boolean deleteUser(UserForm deleteUser)
    {
        Optional<User> userOpt = userRepository.findByPhoneAndEmail(deleteUser.getPhone(), deleteUser.getEmail());
        if (userOpt.isEmpty())
            return false;
        else
        {
            userRepository.deleteById(userOpt.get().getId());
            return true;
        }
    }

    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }
}