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

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

        payment.setApartment(apartment);
        apartment.setPayment(payment);

        paymentRepository.save(payment);
        apartmentRepository.save(apartment);
    }

    public Double calculateTotalPayment(Apartment apartment)
    {
        return apartment.getServices().stream()
                .mapToDouble(ru.yuriy.propertyrental.models.entity.Service::getServicePrice)
                .sum() + apartment.getDailyPrice();
    }

    @Transactional(readOnly = true)
    public Payment getById(Long id)
    {
        return paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Payment> getAllPaymentsFromUser(Principal principal)
    {
        User user = userService.getUserByUsername(principal.getName());
        return paymentRepository.findAllByUser(user);
    }

    @Transactional
    public void payApartment(Long id, Principal principal)
    {
        User user = userService.getUserByUsername(principal.getName());
        AtomicBoolean flag = new AtomicBoolean(false);
        user.getPayments().forEach(
                (payment) -> {
                    if (payment.getId().longValue() == id.longValue())
                    {
                        flag.set(true);
                        if (!payment.getStatus().name().equals(PaymentStatus.PAID.name()))
                        {
                            payment.setStatus(PaymentStatus.PAID);
                            paymentRepository.save(payment);
                        }
                    }
                }
        );

        if (!flag.get())
            throw new PaymentNotFoundException(
                    String.format("Платёж с id=%d для пользователя с id=%d не найден", id, user.getId()));
    }

    @Transactional(readOnly = true)
    public void deleteApartment(Long id, Principal principal)
    {
        User user = userService.getUserByUsername(principal.getName());
        AtomicBoolean flag = new AtomicBoolean(false);
        user.getPayments().forEach(
                (payment) -> {
                    if (payment.getId().longValue() == id.longValue())
                    {
                        flag.set(true);
                        paymentRepository.delete(payment);
                    }
                }
        );
        if (!flag.get())
            throw new PaymentNotFoundException(
                    String.format("Платёж с id=%d для пользователя с id=%d не найден", id, user.getId()));
    }

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