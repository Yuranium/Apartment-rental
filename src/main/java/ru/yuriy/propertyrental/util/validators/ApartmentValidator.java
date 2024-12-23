package ru.yuriy.propertyrental.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import ru.yuriy.propertyrental.models.ApartmentForm;

import java.util.ArrayList;
import java.util.List;

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
        List<MultipartFile> multipartFiles = apartmentForm.getImages();
        if (multipartFiles.size() > 5)
            errors.rejectValue("images", "", "Количество файлов должно быть не больше 5!");
        if (multipartFiles.get(0).isEmpty())
            errors.rejectValue("images", "", "Должна присутствовать хотя-бы одна фотография!");
        else
        {
            boolean flag = false;
            List<String> invalidFileFormats = new ArrayList<>();
            for (MultipartFile file : multipartFiles)
                if (!file.getContentType().startsWith("image"))
                {
                    invalidFileFormats.add(file.getOriginalFilename());
                    flag = true;
                }
            if (flag)
                errors.rejectValue("images", "", "Недопустимый формат файлов: " +
                        String.join(", ", invalidFileFormats));
        }
    }
}