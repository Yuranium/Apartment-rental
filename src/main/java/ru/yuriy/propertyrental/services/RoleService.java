package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.entity.Role;
import ru.yuriy.propertyrental.repositories.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService
{
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<Role> allRoles()
    {
        return roleRepository.findAll();
    }
}