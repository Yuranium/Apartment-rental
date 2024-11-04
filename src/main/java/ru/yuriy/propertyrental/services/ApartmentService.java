package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yuriy.propertyrental.models.ApartmentForm;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.entity.Image;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.repositories.ImageRepository;
import ru.yuriy.propertyrental.repositories.ServiceRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentService
{
    private final ApartmentRepository apartmentRepository;

    private final ApartmentESService apartmentESService;

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Apartment> apartmentList()
    {
        return apartmentRepository.findAll();
    }

    @Transactional
    public void saveApartment(ApartmentForm newApartment)
    {
        Apartment apartment = new Apartment();
        apartment.setName(newApartment.getName());
        apartment.setSquare(newApartment.getSquare());
        apartment.setRoomCount(newApartment.getRoomCount());
        apartment.setDailyPrice(newApartment.getDailyPrice());
        apartment.setType(newApartment.getType());
        apartment.setAddress(newApartment.getAddress());
        apartment.setRoomAvailable(true);
        apartment.setServicesToApartment(newApartment.getServices());
        if (!newApartment.getImages().isEmpty())
        {
            apartment.setImagesToApartment(multipartToImage(newApartment.getImages()));
            apartment.getImages().get(0).setPreviewImage(true);
            imageRepository.saveAll(apartment.getImages());
        }
        apartmentRepository.save(apartment);
    }

    private List<Image> multipartToImage(List<MultipartFile> files)
    {
        return files.stream()
                .map(file -> {
                    Image image = new Image();
                    image.setName(file.getName());
                    image.setContentType(file.getContentType());
                    image.setSize(file.getSize());
                    try {
                        image.setImageBytes(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return image;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteApartment(Long id)
    {
        apartmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Apartment> getSearchApartment(ApartmentSearch apartmentSearch)
    {
        List<ApartmentSearch> apartmentSearchList = apartmentESService.getSearchApartments(apartmentSearch);
        return apartmentRepository.findAllById(apartmentSearchList.stream()
                .map(ApartmentSearch::getId)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public Apartment findApartmentById(Long id)
    {
        return apartmentRepository.findById(id).orElseThrow(null);
    }
}