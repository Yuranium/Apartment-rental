package ru.yuriy.propertyrental.controllers;

import jakarta.servlet.http.HttpSession;
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
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.services.EmailService;
import ru.yuriy.propertyrental.services.UserService;
import ru.yuriy.propertyrental.util.CodeValidator;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ConfirmCodeController
{
    private final EmailService emailService;

    private final CodeValidator codeValidator;

    private final UserService userService;

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam(name = "repeat", required = false) Boolean repeat,
                                      Model model)
    {
        model.addAttribute("confirmCode", new ConfirmCode());
        if (Optional.ofNullable(repeat).isPresent())
            emailService.repeatSendEmail();
        return "confirm";
    }

    @PostMapping("/confirm")
    public String confirmRegistration(@ModelAttribute @Valid ConfirmCode confirmCode,
                                      BindingResult result, Model model, HttpSession session)
    {
        codeValidator.validate(confirmCode, result);
        model.addAttribute("errors", result);
        if (result.hasErrors())
            return "confirm";
        else
        {
            User user = (User) session.getAttribute("user");
            userService.setActive(user);
            return "redirect:/";
        }
    }
}