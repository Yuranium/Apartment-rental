package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yuriy.propertyrental.models.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}