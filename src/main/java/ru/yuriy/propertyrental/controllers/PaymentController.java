package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.services.PaymentService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController
{
    private final PaymentService paymentService;

    @GetMapping("/book/{apartment}")
    public String associateApartmentUser(@PathVariable Apartment apartment, Principal principal)
    {
        if (principal == null)
            return "redirect:/registration?noAuth=true";
        paymentService.addPaymentTheUser(apartment, principal.getName());
        return "redirect:/";
    }
}