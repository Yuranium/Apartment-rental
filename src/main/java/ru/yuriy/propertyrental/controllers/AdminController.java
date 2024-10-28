package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.UserNotFoundException;

@Controller
@RequiredArgsConstructor
public class AdminController
{
    private final UserService userService;

    private final ApartmentService apartmentService;

    @GetMapping("/admin/all-users")
    public String allUsers(Model model)
    {
        model.addAttribute("allUsers", userService.findAll());
        return "allUsers";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model)
    {
        model.addAttribute("userEdit", userService.findById(id));
        return "userProfile";
    }

    @GetMapping("/admin/ban/{id}")
    public String banUser(@PathVariable Long id)
    {
        userService.banUserById(id);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/admin/unban/{id}")
    public String unbanUser(@PathVariable Long id)
    {
        userService.unbanUserById(id);
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