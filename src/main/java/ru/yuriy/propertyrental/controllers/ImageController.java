package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.entity.Image;
import ru.yuriy.propertyrental.services.ImageService;
import ru.yuriy.propertyrental.util.response_body.ImageErrorResponse;
import ru.yuriy.propertyrental.util.exceptions.ImageNotFoundException;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController
{
    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id)
    {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok()
                .header("filename", image.getName())
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getImageBytes())));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<?> imageHandler(ImageNotFoundException exc)
    {
        return new ResponseEntity<>(ImageErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .code(HttpStatus.NOT_FOUND.value())
                .errorMessage(exc.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build(), HttpStatus.NOT_FOUND);
    }
}