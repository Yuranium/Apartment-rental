package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.UserNotFoundException;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService
{
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        return new MyUserDetails(user);
    }
}