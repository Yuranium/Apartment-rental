package ru.yuriy.propertyrental.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yuriy.propertyrental.models.ConfirmCode;
import ru.yuriy.propertyrental.services.EmailService;

@Component
@RequiredArgsConstructor
public class CodeValidator implements Validator
{
    private final EmailService emailService;
    @Override
    public boolean supports(Class<?> clazz)
    {
        return ConfirmCode.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        ConfirmCode code = (ConfirmCode) target;
        if (!code.toString().equals(emailService.getCode()))
            errors.rejectValue("number1", "", "Введённый код некорректен!");
    }
}