package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.yuriy.propertyrental.enums.RoleType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role
{
    @Id
    @Column(name = "id_role", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type", length = 50)
    private RoleType roleType;

    @Column(name = "assignment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp assignmentDate;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @PrePersist
    private void currentTimestamp()
    {
        this.assignmentDate = new Timestamp(System.currentTimeMillis());
    }
}