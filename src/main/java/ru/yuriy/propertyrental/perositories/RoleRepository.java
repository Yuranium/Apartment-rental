package ru.yuriy.propertyrental.perositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yuriy.propertyrental.models.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}