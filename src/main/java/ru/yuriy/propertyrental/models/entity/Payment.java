package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ru.yuriy.propertyrental.enums.PaymentStatus;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment", nullable = false)
    private Long id;

    @Column(name = "amount_payment", columnDefinition = "numeric(10, 3)")
    private Double amountPayment;

    @Column(name = "date_payment", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    private Date datePayment;

    @Column(name = "status_payment", length = 20)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne(mappedBy = "payment", orphanRemoval = true)
    private Apartment apartment;

    public void setApartment(Apartment apartment)
    {
        apartment.setPayment(this);
        this.apartment = apartment;
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Payment payment = (Payment) o;
        return getId() != null && Objects.equals(getId(), payment.getId());
    }

    @Override
    public final int hashCode()
    {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}