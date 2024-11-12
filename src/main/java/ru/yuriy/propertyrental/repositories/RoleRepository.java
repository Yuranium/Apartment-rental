package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.enums.RoleType;
import ru.yuriy.propertyrental.models.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    List<Role> findAllByRoleTypeIn(List<RoleType> roleType);
}