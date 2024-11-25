package ru.yuriy.propertyrental.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.UserForm;
import ru.yuriy.propertyrental.models.dto.UserDTO;
import ru.yuriy.propertyrental.services.rest.UserRestService;
import ru.yuriy.propertyrental.util.RestErrorHandler;
import ru.yuriy.propertyrental.util.response_body.ErrorResponse;
import ru.yuriy.propertyrental.util.response_body.ValidResponse;
import ru.yuriy.propertyrental.util.validators.UserValidator;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController
{
    private final UserRestService userService;

    private final UserValidator userValidator;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid UserForm userForm, BindingResult result)
    {
        userValidator.validate(userForm, result);
        if (result.hasErrors())
            return new ResponseEntity<>(
                    ValidResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .code(HttpStatus.BAD_REQUEST.value())
                            .timestamp(new Timestamp(System.currentTimeMillis()))
                            .errorMessages(result.getFieldErrors().stream()
                                    .collect(Collectors.groupingBy(
                                            FieldError::getField,
                                            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                                    )))
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> allUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size)
    {
        return new ResponseEntity<>(
                userService.listUsers(PageRequest.of(page, size)), HttpStatus.OK
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

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO user)
    {
        userService.updateUser(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestParam String email)
    {
        userService.deleteUser(id, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorResponse> exceptionHandle(AccessDeniedException exc)
    {
        return new ResponseEntity<>(RestErrorHandler.createMessage(HttpStatus.FORBIDDEN, exc),
                HttpStatus.FORBIDDEN);
    }
}