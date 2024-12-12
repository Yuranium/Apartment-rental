package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.enums.PaymentStatus;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.PaymentRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class PaymentServiceProxy
{
    private final PaymentRepository paymentRepository;

    private final EmailService emailService;

    @Transactional
    public void checkPaymentStatus(User user)
    {
        AtomicBoolean flag = new AtomicBoolean(false);
        List<Payment> payments = paymentRepository.findAllByUser(user);
        payments.forEach(pay -> {
            if (pay.isOverduePayment())
            {
                pay.setStatus(PaymentStatus.OVERDUE);
                flag.set(true);
            }
        });

        if (flag.get())
            CompletableFuture.runAsync(
                    () -> emailService.sendMessageLatePayment(user.getEmail(), user.getName(),
                            payments.stream()
                                    .filter(payment -> payment.getStatus()
                                            .name()
                                            .equals(PaymentStatus.OVERDUE.name()))
                                    .toList())
            );
        paymentRepository.saveAll(payments);
    }
}