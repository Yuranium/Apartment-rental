package ru.yuriy.propertyrental.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.ApartmentForm;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;

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

    private final ImageService imageService;

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
        apartment.setServices(newApartment.getServices());
        apartment.setUser(getUserByPrincipal(principal));
        if (!newApartment.getImages().isEmpty())
        {
            apartment.setImages(
                    imageService.multipartToImage(newApartment.getImages())
            );
            apartment.getImages().get(0).setPreviewImage(true);
            imageService.saveAll(apartment.getImages());
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
        List<Apartment> apartments = apartmentRepository.findAll(
                PageRequest.of(0, 6)).getContent();
        apartments.forEach(apartment -> {
            apartment.getImages().size();
            apartment.getServices().size();
        });
        return apartments;
    }
}