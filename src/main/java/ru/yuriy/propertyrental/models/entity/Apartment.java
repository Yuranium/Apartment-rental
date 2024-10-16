package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.yuriy.propertyrental.enums.ApartmentType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "apartments")
@AllArgsConstructor
@NoArgsConstructor
public class Apartment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apartment", nullable = false)
    private Long id;

    @Column(name = "square", columnDefinition = "numeric(10, 3)")
    private Double square;

    @Column(name = "room_count")
    private Integer roomCount;

    @Column(name = "daily_price", columnDefinition = "numeric(10, 3)")
    private Double dailyPrice;

    @Column(name = "apartment_type", length = 50)
    @Enumerated(value = EnumType.STRING)
    private ApartmentType type;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "room_available")
    private Boolean roomAvailable;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "id_payment", referencedColumnName = "id_payment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "apartment", orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "apartments_services",
            joinColumns = @JoinColumn(name = "id_apartment"),
            inverseJoinColumns = @JoinColumn(name = "id_service"))
    private List<Service> services = new ArrayList<>();
}