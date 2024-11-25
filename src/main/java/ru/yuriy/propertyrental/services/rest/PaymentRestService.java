package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.dto.PaymentDTO;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.mappers.PaymentMapper;
import ru.yuriy.propertyrental.util.exceptions.PaymentNotFoundException;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentRestService
{
    private final UserRepository userRepository;

    private final PaymentMapper paymentMapper;

    @Transactional(readOnly = true)
    public Set<PaymentDTO> allPayments(Long userId)
    {
        return paymentMapper.listToDTO(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Пользователь с id=%d не был найден", userId)))
                .getPayments());
    }

    @Transactional(readOnly = true)
    public PaymentDTO getPaymentByUser(Long userId, Long id)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Пользователь с id=%d не был найден", userId)));

        for (Payment payment : user.getPayments())
            if (payment.getId().longValue() == id.longValue())
                return paymentMapper.toDTO(payment);
        throw new PaymentNotFoundException(
                String.format("Платёж с id=%d для пользователя с userID=%d не был найден!", id, userId)
        );
    }
}