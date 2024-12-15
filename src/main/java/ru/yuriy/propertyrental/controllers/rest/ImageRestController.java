package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yuriy.propertyrental.models.dto.ImageDTO;
import ru.yuriy.propertyrental.services.ImageService;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageRestController
{
    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id)
    {
        ImageDTO image = imageService.getImage(id);
        return ResponseEntity.ok()
                .header("filename", image.name())
                .contentType(MediaType.parseMediaType(image.contentType()))
                .contentLength(image.size())
                .body(new InputStreamResource(new ByteArrayInputStream(image.imageBytes())));
    }
}