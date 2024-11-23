package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.dto.UserDTO;
import ru.yuriy.propertyrental.services.rest.UserRestService;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;
import ru.yuriy.propertyrental.util.response_body.ErrorResponse;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController
{
    private final UserRestService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> allUsers(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize)
    {
        return new ResponseEntity<>(
                userService.listUsers(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id)
    {
        return new ResponseEntity<>(
                userService.getById(id), HttpStatus.OK
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorResponse> exceptionHandle(UserNotFoundException exc)
    {
        return new ResponseEntity<>(ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .code(HttpStatus.NOT_FOUND.value())
                .errorMessage(exc.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build(), HttpStatus.NOT_FOUND);
    }
}