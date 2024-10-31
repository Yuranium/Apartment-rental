package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.models.entity.Role;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.RoleRepository;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.RoleNotFoundException;
import ru.yuriy.propertyrental.util.UserNotFoundException;

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
    @Transactional(rollbackFor = {RoleNotFoundException.class})
    public void saveUser(UserForm userForm)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        String lastName = user.getLastName();
        String phone = user.getPhone();
        user.setName(userForm.getName());
        user.setLastName(lastName == null || lastName.isBlank() ? null : lastName);
        user.setEmail(userForm.getEmail());
        user.setPhone(phone == null || phone.isBlank() ? null : phone);
        user.setPassword(userForm.getPassword());
        user.setBirthday(userForm.getBirthday().isBlank() ? null :
                dateFormat.parse(userForm.getBirthday()));
        user.setActive(false);
        user = userRepository.save(user);
        Role role = roleRepository.findById(2L).orElseThrow(() -> new RoleNotFoundException(
                "ОШИБКА: Для данного пользователя роль не была установлена"));
        user.getRoles().add(role);
        role.getUsers().add(user);

        roleRepository.save(role);
        userRepository.save(user);
    }

    @Transactional
    public boolean updateUser(UserForm userForm)
    {
        Optional<User> userOpt = userRepository.findByEmail(userForm.getEmail());
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
        Optional<User> userOpt = userRepository.findByEmail(deleteUser.getEmail());
        if (userOpt.isEmpty())
            return false;
        else
        {
            userRepository.deleteById(userOpt.get().getId());
            return true;
        }
    }

    @Transactional
    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    @Transactional
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    @Transactional
    public void banUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Данный пользователь не был найден!"));
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void unbanUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Данный пользователь не был найден!"));
        user.setActive(true);
        userRepository.save(user);
    }
}