package ru.yuriy.propertyrental.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yuriy.propertyrental.models.ApartmentForm;

import java.io.IOException;

@Component
public class ApartmentValidator implements Validator
{
    @Override
    public boolean supports(Class<?> clazz)
    {
        return ApartmentForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        ApartmentForm apartmentForm = (ApartmentForm) target;
        if (apartmentForm.getImages().size() > 5)
            errors.rejectValue("images", "", "Количество файлов должно быть не больше 5!");
        try {
            if (apartmentForm.getImages().get(0).getBytes().length == 0)
                errors.rejectValue("images", "", "Должна присутствовать хотя-бы одна фотография!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}