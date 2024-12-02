package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.entity.Image;
import ru.yuriy.propertyrental.repositories.ImageRepository;
import ru.yuriy.propertyrental.util.exceptions.ImageNotFoundException;

@RequiredArgsConstructor
@Service
public class ImageService
{
    private final ImageRepository imageRepository;

    public Image getImage(Long id)
    {
        return imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException("Данная фотография не была найдена"));
    }
}