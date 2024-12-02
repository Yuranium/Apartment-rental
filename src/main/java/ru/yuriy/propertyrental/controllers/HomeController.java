package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.services.RoleService;
import ru.yuriy.propertyrental.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController
{
    private final UserService userService;

    private final RoleService roleService;

    private final ApartmentService apartmentService;

    @GetMapping
    public String home(Principal principal, Model model)
    {
        model.addAttribute("apartments", apartmentService.getApartments());
        if (principal != null)
        {
            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("currentUser", user);
            model.addAttribute("roles", roleService.rolesEntityToString(user.getRoles()));
        }
        else model.addAttribute("currentUser", null);
        return "home";
    }
}