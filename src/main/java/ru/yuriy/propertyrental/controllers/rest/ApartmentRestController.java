package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.yuriy.propertyrental.services.rest.ApartmentRestService;

@RestController
@RequiredArgsConstructor
public class ApartmentRestController
{
    private final ApartmentRestService apartmentService;
}