package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.enums.PaymentStatus;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.PaymentRepository;
import ru.yuriy.propertyrental.util.exceptions.PaymentNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceProxy
{
    private final PaymentRepository paymentRepository;

    @Transactional
    public void checkPaymentStatus(List<Payment> payments)
    {
        if (payments == null) throw new PaymentNotFoundException("Платежи отсутствуют!");
        payments.forEach(pay -> {
            if (pay.isOverduePayment())
                pay.setStatus(PaymentStatus.OVERDUE);
        });
        paymentRepository.saveAll(payments);
    }

    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByUser(User user)
    {
        return paymentRepository.findAllByUser(user);
    }
}