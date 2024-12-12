package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.enums.PaymentStatus;
import ru.yuriy.propertyrental.models.entity.Payment;
import ru.yuriy.propertyrental.models.entity.User;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"apartment"})
    List<Payment> findAllByUser(User user);

    @Query("SELECT p.datePayment, COUNT(*) FROM Payment p WHERE p.user = :user GROUP BY p.datePayment")
    List<Object[]> findAllByUserGrouping(User user);

    List<Payment> findAllByStatusAndUser(PaymentStatus status, User user);
}