package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.PaymentRepository;
import ru.yuriy.propertyrental.util.exceptions.ImageNotFoundException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RabbitService
{
    private final AmqpTemplate template;

    private final PaymentRepository paymentRepository;

    public byte[] sendPaymentData(final User user)
    {
        template.convertAndSend(user.getPayments().stream()
                .collect(Collectors.toMap(
                        Payment::getDatePayment,
                        (payment) -> paymentRepository.getCountPayment(payment.getDatePayment(), user)
                ))
        );

        Message response = template.receive("image_queue", 5000);
        if (response != null && response.getBody().length == 0)
            return response.getBody();
        else throw new ImageNotFoundException("Не удалось получить график платежей");
    }
}