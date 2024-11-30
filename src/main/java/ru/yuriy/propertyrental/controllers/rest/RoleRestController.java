package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yuriy.propertyrental.models.dto.RoleDTO;
import ru.yuriy.propertyrental.models.graphql.input.RoleInput;
import ru.yuriy.propertyrental.services.rest.RoleRestService;
import ru.yuriy.propertyrental.util.response_body.Message;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleRestController
{
    private final RoleRestService roleService;

    @QueryMapping
    public List<RoleDTO> roles()
    {
        return roleService.listRoles();
    }

    @QueryMapping
    public RoleDTO roleById(@Argument Long id)
    {
        return roleService.getById(id);
    }

    @MutationMapping
    public RoleDTO addRole(@Argument RoleInput role)
    {
        return roleService.saveRole(role);
    }

    @MutationMapping
    public Message deleteRole(@Argument Long id)
    {
        roleService.deleteRole(id);
        return new Message("Роль с id=" + id + " удалена",
                200, new Timestamp(System.currentTimeMillis()));
    }
}
