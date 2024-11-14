package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.repositories.PaymentRepository;
import ru.yuriy.propertyrental.repositories.UserRepository;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@RequestMapping("/payment")
public class PaymentService
{
    private final PaymentRepository paymentRepository;

    private final UserService userService;

    @GetMapping("/book")
    public void setPaymentTheUser(Apartment apartment, Principal principal)
    {
        //userService.saveUser(userService.findByEmail());
        paymentRepository.setPaymentTheUser(apartment, principal);
    }
}
