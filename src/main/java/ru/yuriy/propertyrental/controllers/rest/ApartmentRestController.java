package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.services.rest.ApartmentRestService;
import ru.yuriy.propertyrental.util.exceptions.ApartmentNotFoundException;
import ru.yuriy.propertyrental.util.response_body.ErrorResponse;

import java.sql.Timestamp;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getApartment(@PathVariable Long id)
    {
        return new ResponseEntity<>(apartmentService.getApartment(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> addApartment(@RequestBody ApartmentDTO apartment)
    {
        apartmentService.addApartment(apartment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler(ApartmentNotFoundException.class)
    private ResponseEntity<ErrorResponse> exceptionHandle(ApartmentNotFoundException exc)
    {
        return new ResponseEntity<>(createMessage(HttpStatus.NOT_FOUND, exc),
                HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createMessage(HttpStatus status, Throwable throwable)
    {
        return ErrorResponse.builder()
                .status(status.getReasonPhrase())
                .code(status.value())
                .errorMessage(throwable.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}