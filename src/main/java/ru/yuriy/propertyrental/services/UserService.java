package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.enums.RoleType;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.models.entity.Role;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.RoleRepository;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.exceptions.RoleNotFoundException;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @SneakyThrows
    @Transactional(rollbackFor = {RoleNotFoundException.class})
    public User saveUser(UserForm userForm)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        String lastName = userForm.getLastName();
        String phone = userForm.getPhone();
        user.setName(userForm.getName());
        user.setLastName(lastName == null || lastName.isBlank() ? null : lastName);
        user.setEmail(userForm.getEmail());
        user.setPhone(phone == null || phone.isBlank() ? null : phone);
        user.setPassword(encoder.encode(userForm.getPassword()));
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
        return user;
    }

    @Transactional
    public void setUserRoles(User user, List<RoleType> roles)
    {
        List<Role> roles1 = roleRepository.findAllByRoleTypeIn(roles);
        user.setRoles(roles1);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    @Transactional
    public void banOrUnbanUser(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Данный пользователь не был найден!"));
        user.setActive(!user.getActive());
        userRepository.save(user);
    }

    @Transactional
    public void setActive(User user)
    {
        user.setActive(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username)
    {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("Данный пользователь не был найден!"));
    }
}