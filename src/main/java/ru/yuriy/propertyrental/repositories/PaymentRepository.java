package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
    List<Payment> findAllByUser(User user);
}