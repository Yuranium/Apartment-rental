package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/user")
    public String userPayments(Model model, Principal principal)
    {
        model.addAttribute("userPayments", paymentService.getAllPaymentsFromUser(principal));
        return "userPayments";
    }

    @GetMapping("/pay/{id}")
    public String payApartment(@PathVariable Long id, Principal principal)
    {
        paymentService.payApartment(id, principal);
        return "redirect:/payment/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id, Principal principal)
    {
        paymentService.deleteApartment(id, principal);
        return "redirect:/payment/user";
    }
}