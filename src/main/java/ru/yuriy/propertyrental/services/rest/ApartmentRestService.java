package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;

@RequiredArgsConstructor
@Service
public class ApartmentRestService
{
    private final ApartmentRepository apartmentRepository;
}