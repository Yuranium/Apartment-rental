package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.enums.RoleType;
import ru.yuriy.propertyrental.models.entity.Role;
import ru.yuriy.propertyrental.repositories.RoleRepository;
import ru.yuriy.propertyrental.util.exceptions.RoleNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleService
{
    private final RoleRepository roleRepository;

    @Transactional
    public Role saveRole(Role role)
    {
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id)
    {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(
                "ОШИБКА: Для данного пользователя роль не была установлена"));
    }

    public List<Role> getRoleByRoleType(List<RoleType> roles)
    {
        return roleRepository.findAllByRoleTypeIn(roles);
    }

    public String rolesEntityToString(List<Role> roles)
    {
        return roles.stream()
            .map(role -> role
                    .getRoleType()
                    .name())
                .collect(Collectors.joining(", "));
    }
}