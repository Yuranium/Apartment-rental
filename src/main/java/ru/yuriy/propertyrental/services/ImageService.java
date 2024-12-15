package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.dto.ImageDTO;
import ru.yuriy.propertyrental.repositories.ImageRepository;
import ru.yuriy.propertyrental.util.exceptions.ImageNotFoundException;
import ru.yuriy.propertyrental.util.mappers.ImageMapper;

@Service
@RequiredArgsConstructor
public class ImageService
{
    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    @Cacheable(cacheNames = "rest_image", key = "#id")
    public ImageDTO getImage(Long id)
    {
        return imageMapper.toDTO(imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException(
                        String.format("Фотография с id=%d не была найдена", id))
        ));
    }
}