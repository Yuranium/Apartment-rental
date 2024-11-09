package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.UserNotFoundException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController
{
    private final UserService userService;

    private final ApartmentService apartmentService;

    @GetMapping("/all-users")
    public String allUsers(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "adminPanel";
    }

    @GetMapping("/edit/{id}")
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

    @GetMapping("/delete-ap/{id}")
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