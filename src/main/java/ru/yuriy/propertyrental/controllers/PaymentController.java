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

    @GetMapping("/user/{id}")
    public String userPayments(@PathVariable Long id, Model model)
    {
        model.addAttribute("userPayments", paymentService.getAllPaymentsFromUser(id));
        return "userPayments";
    }

    @GetMapping("/pay/{id}")
    public String payApartment(@PathVariable Long id) // todo добавить проверку по аккаунту для оплаты
    {
        paymentService.payApartment(id);
        return "redirect:/user" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id, Principal principal)
    {
        paymentService.deleteApartment(id, principal);
        return "redirect:/user" + id;
    }
}