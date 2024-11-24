package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.repositories.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentRestService
{
    private final PaymentRepository paymentRepository;
}