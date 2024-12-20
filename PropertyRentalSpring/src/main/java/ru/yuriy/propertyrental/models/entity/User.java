package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 5)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
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

    public void setRoles(List<Role> roles)
    {
        List<User> users = new ArrayList<>(List.of(this));
        List<Role> currentRoles = new ArrayList<>();
        if (roles == null) return;
        for (Role role : roles)
        {
            role.setUsers(users);
            currentRoles.add(role);
        }
        this.roles = currentRoles;
    }

    public void setApartments(Apartment apartment)
    {
        if (apartment == null) return;
        apartment.setUser(this);
        this.apartments.add(apartment);
    }
}