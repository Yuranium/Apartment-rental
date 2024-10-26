package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "date_registration")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateRegistration;

    @Column(name = "birthday_date")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Payment> payments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Apartment> apartments = new ArrayList<>();
    @PrePersist
    private void currentTimestamp()
    {
        dateRegistration = new Timestamp(System.currentTimeMillis());
    }
}