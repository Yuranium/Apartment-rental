package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.enums.PaymentStatus;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.repositories.PaymentRepository;
import ru.yuriy.propertyrental.util.exceptions.PaymentNotFoundException;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService
{
    private final PaymentRepository paymentRepository;

    private final ApartmentRepository apartmentRepository;

    private final UserService userService;

    @Transactional
    public void addPaymentTheUser(Apartment apartment, String username)
    {
        apartment.setRoomAvailable(false);
        apartmentRepository.save(apartment);

        Payment payment = new Payment();
        payment.setAmountPayment(calculateTotalPayment(apartment));
        payment.setDatePayment(new Date());
        payment.setStatus(PaymentStatus.NOT_PAID);
        payment.setApartment(apartment);
        User user = userService.getUserByUsername(username);
        user.getPayments().add(payment);
        payment.setUser(user);

        paymentRepository.save(payment);
    }

    public Double calculateTotalPayment(Apartment apartment)
    {
        return apartment.getServices().stream()
                .mapToDouble(ru.yuriy.propertyrental.models.entity.Service::getServicePrice)
                .sum() + apartment.getDailyPrice();
    }

    public Payment findById(Long id)
    {
        return paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    public List<Payment> getAllPaymentsFromUser(Long id)
    {
        User user = userService.findById(id).orElseThrow(
                () -> new UserNotFoundException(
                        "Пользователь с id=" + id + " не найден!")
        );
        return paymentRepository.findAllByUser(user);
    }

    public void payApartment(Long id)
    {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
    }

    public void deleteApartment(Long id, Principal principal)
    {
        User user = userService.getUserByUsername(principal.getName());
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
        user.deletePayment(payment);
    }
}