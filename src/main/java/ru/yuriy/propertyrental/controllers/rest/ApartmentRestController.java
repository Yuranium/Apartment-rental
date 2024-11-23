package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yuriy.propertyrental.services.rest.ApartmentRestService;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
public class ApartmentRestController
{
    private final ApartmentRestService apartmentService;

    @GetMapping("/all")
    public ResponseEntity<?> getListApartment(@RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "10") Integer size)
    {
        return new ResponseEntity<>(
                apartmentService.listApartments(PageRequest.of(page, size)), HttpStatus.OK
        );
    }
}