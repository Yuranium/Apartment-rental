package ru.yuriy.propertyrental.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yuriy.propertyrental.models.ConfirmCode;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.services.EmailService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.CodeValidator;
import ru.yuriy.propertyrental.util.UserValidator;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    private final UserValidator userValidator;

    private final EmailService emailService;

    private final CodeValidator codeValidator;

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
        userService.saveUser(userForm);
        emailService.sendHtmlEmail(userForm.getEmail());
        // else model.addAttribute("userForm", userForm);
        return "redirect:/confirm";
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam(name = "repeat", required = false) Boolean repeat,
                                      Model model)
    {
        model.addAttribute("code", new ConfirmCode());
        if (Optional.ofNullable(repeat).isPresent())
            emailService.repeatSendEmail();
        return "confirm";
    }

    @PostMapping("/confirm")
    public String confirmRegistration(@ModelAttribute @Valid ConfirmCode code,
                                      BindingResult result, Model model)
    {
        codeValidator.validate(code, result);
        model.addAttribute("codeError", result);
        if (result.hasErrors())
            return "confirm";
        else return "redirect:/"; // todo устанавливать активность через сессии
    }
}