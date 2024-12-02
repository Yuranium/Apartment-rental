package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.dto.RoleDTO;
import ru.yuriy.propertyrental.models.graphql.input.RoleInput;
import ru.yuriy.propertyrental.repositories.RoleRepository;
import ru.yuriy.propertyrental.util.exceptions.RoleNotFoundException;
import ru.yuriy.propertyrental.util.mappers.RoleMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleRestService
{
    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public List<RoleDTO> listRoles()
    {
        return roleMapper.listToDTO(
                roleRepository.findAll()
        );
    }

    public RoleDTO getById(Long id)
    {
        return roleMapper.toDTO(
                roleRepository.findById(id).orElseThrow(
                        () -> new RoleNotFoundException(
                                "ОШИБКА: Роль не была найдена")
                )
        );
    }

    public RoleDTO saveRole(RoleInput role)
    {
        return roleMapper.toDTO(roleRepository.save(
                roleMapper.toEntity(role)
        ));
    }

    public void deleteRole(Long id)
    {
        roleRepository.deleteById(id);
    }
}