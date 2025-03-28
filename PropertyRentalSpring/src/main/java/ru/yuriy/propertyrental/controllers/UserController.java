package ru.yuriy.propertyrental.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.services.EmailService;
import ru.yuriy.propertyrental.services.RoleService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.validators.UserValidator;

import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    private final UserValidator userValidator;

    private final EmailService emailService;

    private final RoleService roleService;

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(required = false, defaultValue = "false") boolean noAuth)
    {
        if (noAuth) model.addAttribute("noAuth", "Для аренды нужно зарегистрироваться!");
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid UserForm userForm,
                               BindingResult result, Model model, HttpSession session)
    {
        userValidator.validate(userForm, result);
        model.addAttribute("errors", result);
        if (result.hasErrors())
            return "registration";
        session.setAttribute("user", userService.saveUser(userForm));
        emailService.sendHtmlEmail(userForm.getEmail());
        return "redirect:/confirm";
    }

    @GetMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("userForm", new UserForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserForm userForm,
                        BindingResult result, Model model)
    {
        userValidator.validate(userForm, result);
        model.addAttribute("errors", result);
        if (result.hasErrors())
            return "login";
        return "redirect:/home";
    }

    @GetMapping("profile/{id}")
    public String userProfile(@PathVariable Long id, Model model, Principal principal)
    {
        Optional<User> user = userService.findById(id);
        if (user.isPresent())
        {
            model.addAttribute("dataGraph", Base64.getEncoder().encodeToString(
                    userService.compilingDataGraph(user.get())
            ));
            model.addAttribute("username", principal.getName());
            model.addAttribute("profileInfo", user.get());
            model.addAttribute("roles", roleService.rolesEntityToString(user.get().getRoles()));
            return "userProfile";
        }
        else return "404";
    }

    @DeleteMapping("/deleteProfile/{id}")
    public String deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return "redirect:/home";
    }
}