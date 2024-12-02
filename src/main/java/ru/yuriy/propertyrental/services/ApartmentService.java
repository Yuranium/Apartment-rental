package ru.yuriy.propertyrental.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yuriy.propertyrental.models.ApartmentForm;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.entity.Image;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.repositories.ImageRepository;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;

import java.io.IOException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yuriy.propertyrental.controllers.ApartmentController.Sorting;

@Service
@RequiredArgsConstructor
public class ApartmentService
{
    private final ApartmentRepository apartmentRepository;

    private final ApartmentESService apartmentESService;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Apartment> apartmentList()
    {
        return apartmentRepository.findAll();
    }

    @Transactional
    public void saveApartment(ApartmentForm newApartment, Principal principal)
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
        apartment.setUser(getUserByPrincipal(principal));
        if (!newApartment.getImages().isEmpty())
        {
            apartment.setImagesToApartment(multipartToImage(newApartment.getImages()));
            apartment.getImages().get(0).setPreviewImage(true);
            imageRepository.saveAll(apartment.getImages());
        }
        apartmentRepository.save(apartment);
    }

    @Transactional(readOnly = true)
    public User getUserByPrincipal(Principal principal)
    {
        if (principal == null) throw new SessionAuthenticationException("Пользователь не аутентифицирован!");
        return userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Данный пользователь не был найден!"));
    }

    private List<Image> multipartToImage(List<MultipartFile> files)
    {
        return files.stream()
                .map(file -> {
                    Image image = new Image();
                    image.setName(file.getOriginalFilename());
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

    public List<Apartment> sortApartment(Sorting sorting, HttpSession session)
    {
        List<Apartment> apartments = (List<Apartment>) session.getAttribute("apartmentsSearchResult");
        if (sorting.isSquareSort())
            apartments = apartments.stream()
                    .sorted(Comparator.comparing(Apartment::getSquare))
                    .collect(Collectors.toList());
        if (sorting.isRoomsSort())
            apartments = apartments.stream()
                    .sorted(Comparator.comparing(Apartment::getRoomCount))
                    .collect(Collectors.toList());
        if (sorting.isPriceSort())
            apartments = apartments.stream()
                    .sorted(Comparator.comparing(Apartment::getDailyPrice))
                    .collect(Collectors.toList());
        return apartments;
    }

    @Transactional(readOnly = true)
    public Apartment findApartmentById(Long id)
    {
        return apartmentRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с id=" + id + " не был найден")
        );
    }

    @Transactional(readOnly = true)
    public Boolean isApartmentBooked(Long apartmentID, Principal principal)
    {
        try
        {
            User user = getUserByPrincipal(principal);
            return user.getPayments().stream()
                    .anyMatch(pay -> pay.getApartment()
                            .getId().longValue() != apartmentID);
        } catch (SessionAuthenticationException exc)
        {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Apartment> getApartments()
    {
        List<Apartment> apartments = apartmentRepository.findAllForHomePage();
        apartments.forEach(apartment -> {
            apartment.getImages().size();
            apartment.getServices().size();
        });
        return apartments;
    }
}