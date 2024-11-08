package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.UserNotFoundException;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController
{
    private final UserService userService;

    private final ApartmentService apartmentService;

    @GetMapping("/admin/all-users")
    public String allUsers(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "adminPanel";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model)
    {
        model.addAttribute("userEdit", userService.findById(id));
        return "userProfile";
    }

    @PostMapping("/ban/{id}")
    public String banUser(@PathVariable Long id)
    {
        userService.banOrUnbanUser(id);
        return "redirect:/admin/all-users";
    }

    @PostMapping("/unban/{id}")
    public String unbanUser(@PathVariable Long id)
    {
        userService.banOrUnbanUser(id);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/admin/delete-ap/{id}")
    public String deleteApartment(@PathVariable Long id)
    {
        apartmentService.deleteApartment(id);
        return "redirect:/apartments/all";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException exc)
    {
        return "404";
    }
}