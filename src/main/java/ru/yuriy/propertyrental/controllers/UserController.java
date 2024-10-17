package ru.yuriy.propertyrental.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.UserValidator;

@Controller
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    private final UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model)
    {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid UserForm userForm,
                               BindingResult result, Model model)
    {
        userValidator.validate(userForm, result);
        model.addAttribute("errors", result);
        if (result.hasErrors())
            return "registration";
        // else model.addAttribute("userForm", userForm);
        return "redirect:/";
    }
}