package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.yuriy.propertyrental.enums.ApartmentType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "apartments")
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "apartment-image-graph",
        attributeNodes = {
                @NamedAttributeNode("images")
        }
)
public class Apartment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apartment", nullable = false)
    private Long id;

    @Column(name = "name_apartment", length = 50)
    private String name;

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

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payment", referencedColumnName = "id_payment")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @BatchSize(size = 6)
    @OneToMany(mappedBy = "apartment", orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @BatchSize(size = 6)
    @JoinTable(name = "apartments_services",
            joinColumns = @JoinColumn(name = "id_apartment"),
            inverseJoinColumns = @JoinColumn(name = "id_service"))
    private List<Service> services = new ArrayList<>();

    public void setImages(List<Image> images)
    {
        for (Image image : images)
        {
            image.setApartment(this);
            this.images.add(image);
        }
    }

    public void setServices(List<Service> services)
    {
        List<Apartment> apartments = new ArrayList<>(List.of(this));
        if (services == null) return;
        for (Service service : services)
        {
            service.setApartments(apartments);
            this.services.add(service);
        }
    }
}