package ru.yuriy.propertyrental.models;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.services.PaymentServiceProxy;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    private final PaymentServiceProxy paymentService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("ОШИБКА: Пользователь не найден!"));
        if (!user.getActive())
            throw new UsernameNotFoundException("ОШИБКА: Пользователь неактивен");
        paymentService.checkPaymentStatus(paymentService.getPaymentsByUser(user));
        return new MyUserDetails(user);
    }
}