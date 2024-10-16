package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserForm userForm, Model model, BindingResult result)
    {
        model.addAttribute("newUser", userService.saveNewUser(userForm, result));
        return "redirect:/";
    }
}