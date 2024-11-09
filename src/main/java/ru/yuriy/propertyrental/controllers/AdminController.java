package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.enums.RoleType;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/editRoles/{user}")
    public String editRoles(@PathVariable User user, Model model)
    {
        model.addAttribute("editRoleUser", user);
        model.addAttribute("roles", user.getRoles()
                .stream()
                .map(role -> role
                        .getRoleType()
                        .name())
                .collect(Collectors.toList()));
        return "editRoles";
    }

    @PostMapping("/editRoles/{user}")
    public String editRoles(@PathVariable User user, @RequestParam List<String> roleTypes)
    {
        userService.setUserRoles(user, roleTypes);
        return "redirect:/profile/" + user.getId();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException exc)
    {
        return "404";
    }
}