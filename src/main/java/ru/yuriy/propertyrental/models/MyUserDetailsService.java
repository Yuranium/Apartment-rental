package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.UserRepository;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService
{
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("ОШИБКА: Пользователь не найден!"));
        if (!user.getActive())
            throw new UsernameNotFoundException("ОШИБКА: Пользователь неактивен");
        return new MyUserDetails(user);
    }
}