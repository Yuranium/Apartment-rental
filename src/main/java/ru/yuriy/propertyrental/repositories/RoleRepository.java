package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    @Query(value = "SELECT r FROM Role r WHERE r.roleType IN :roles", nativeQuery = true)
    List<Role> findAllByRoleTypes(List<String> roles);
}