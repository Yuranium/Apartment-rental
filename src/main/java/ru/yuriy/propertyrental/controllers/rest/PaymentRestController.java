package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.dto.PaymentDTO;
import ru.yuriy.propertyrental.models.graphql.input.PaymentInput;
import ru.yuriy.propertyrental.services.rest.PaymentRestService;

import java.util.Set;

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

    @QueryMapping
    public Set<PaymentDTO> allPaymentsByUserId(@Argument Long userId)
    {
        return paymentService.allPayments(userId);
    }

    @QueryMapping
    public PaymentDTO paymentById(@Argument Long userId, @Argument Long paymentId)
    {
        return paymentService.getPaymentByUser(userId, paymentId);
    }

    @MutationMapping
    public PaymentDTO updatePayment(@Argument PaymentInput payment,
                                    @Argument Long id)
    {
        return paymentService.updatePayment(payment, id);
    }
}