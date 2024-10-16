package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
public class Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "service_price", columnDefinition = "numeric(10, 3)")
    private Double servicePrice;

    @ManyToMany(mappedBy = "services")
    private List<Apartment> apartments = new ArrayList<>();
}