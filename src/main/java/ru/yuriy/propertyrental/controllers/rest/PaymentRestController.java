package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.services.rest.PaymentRestService;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentRestController
{
    private final PaymentRestService paymentService;

    @GetMapping("/all")
    public ResponseEntity<?> allPayments(@RequestParam(name = "userId") Long userId)
    {
        return new ResponseEntity<>(paymentService.allPayments(userId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentByUser(@RequestParam(name = "userId") Long userId,
                                              @PathVariable Long id)
    {
        return new ResponseEntity<>(paymentService.getPaymentByUser(userId, id),
                HttpStatus.OK);
    }
}