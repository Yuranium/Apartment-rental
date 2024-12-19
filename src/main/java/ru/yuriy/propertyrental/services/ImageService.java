package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yuriy.propertyrental.models.dto.ImageDTO;
import ru.yuriy.propertyrental.models.entity.Image;
import ru.yuriy.propertyrental.repositories.ImageRepository;
import ru.yuriy.propertyrental.util.exceptions.ImageNotFoundException;
import ru.yuriy.propertyrental.util.mappers.ImageMapper;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "rest_image")
public class ImageService
{
    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    private final int MIN_BYTE_SIZE_COMPRESSION = 20_000;

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public ImageDTO getImage(Long id)
    {
        return imageMapper.toDTO(imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException(
                        String.format("Фотография с id=%d не была найдена", id))
        ));
    }

    @Transactional
    public void saveAll(List<Image> images)
    {
        imageRepository.saveAll(images);
    }

    public List<Image> multipartToImage(List<MultipartFile> files, Boolean isCompress)
    {
        return files.parallelStream()
                .map(file -> {
                    Image image = new Image();
                    try {
                        image.setImageBytes(isCompress ?
                                compressImage(file.getBytes(), 0.65F) : file.getBytes()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    image.setName(file.getOriginalFilename());
                    image.setContentType(isCompress ? "image/jpeg" : file.getContentType());
                    return image;
                })
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public byte[] compressImage(byte[] inputBytes, float quality)
    {
        if (inputBytes.length < MIN_BYTE_SIZE_COMPRESSION)
            return inputBytes;
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(inputBytes));
        if (image == null)
            throw new IllegalArgumentException("Некорректное изображение");
        image = resizeImageWithAspectRatio(image);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream))
        {
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
            writer.dispose();

            return outputStream.toByteArray();
        }
    }

    private BufferedImage resizeImageWithAspectRatio(BufferedImage originalImage)
    {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth <= 1920 && originalHeight <= 1080)
            return originalImage;

        double widthRatio = (double) 1920 / originalWidth;
        double heightRatio = (double) 1080 / originalHeight;

        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        java.awt.Image resultingImage = originalImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();

        return outputImage;
    }
}