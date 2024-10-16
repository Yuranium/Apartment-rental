package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.perositories.ApartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService
{
    private final ApartmentRepository apartmentRepository;

    public List<Apartment> apartmentList()
    {
        return apartmentRepository.findAll();
    }
}