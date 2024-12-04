package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.models.graphql.input.ApartmentInput;
import ru.yuriy.propertyrental.services.rest.ApartmentRestService;
import ru.yuriy.propertyrental.util.RestErrorHandler;
import ru.yuriy.propertyrental.util.response_body.ErrorResponse;
import ru.yuriy.propertyrental.util.response_body.Message;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

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

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateApartment(@RequestBody ApartmentDTO apartment,
                                             @PathVariable Long id)
    {
        apartmentService.updateApartment(apartment, id);
        return new ResponseEntity<>("Обновлён только апартамент", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteApartmentById(@PathVariable Long id)
    {
        apartmentService.deleteApartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test/cache")
    public ResponseEntity<ApartmentDTO> testCache()
    {
        return new ResponseEntity<>(apartmentService.cache(new Random()
                .nextLong(1, 10)), HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlExceptionHandle(SQLException exc)
    {
        return new ResponseEntity<>(RestErrorHandler.createMessage(HttpStatus.BAD_REQUEST, exc),
                HttpStatus.BAD_REQUEST);
    }

    @QueryMapping
    public List<ApartmentDTO> apartments(@Argument(name = "page") Integer page,
                                    @Argument(name = "size") Integer size)
    {
        return apartmentService.listApartments(PageRequest.of(page, size));
    }

    @QueryMapping
    public ApartmentDTO apartmentById(@Argument Long id)
    {
        return apartmentService.getApartment(id);
    }

    @MutationMapping
    public ApartmentDTO addApartment(@Argument ApartmentInput apartment)
    {
        return apartmentService.saveApartment(apartment);
    }

    @MutationMapping
    public ApartmentDTO updateApartment(@Argument ApartmentInput apartment,
                                        @Argument Long id)
    {
        return apartmentService.updateApartment(apartment, id);
    }

    @MutationMapping
    public Message deleteApartment(@Argument Long id)
    {
        apartmentService.deleteApartment(id);
        return new Message("Апартамент успешно удалён", 200,
                new Timestamp(System.currentTimeMillis()));
    }
}