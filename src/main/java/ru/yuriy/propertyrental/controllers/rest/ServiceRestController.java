package ru.yuriy.propertyrental.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yuriy.propertyrental.models.dto.ServiceDTO;
import ru.yuriy.propertyrental.models.graphql.input.ServiceInput;
import ru.yuriy.propertyrental.services.rest.ServiceRestService;
import ru.yuriy.propertyrental.util.response_body.Message;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServiceRestController
{
    private final ServiceRestService serviceService;

    @QueryMapping
    public List<ServiceDTO> services(@Argument(name = "page") Integer page,
                                     @Argument(name = "size") Integer size)
    {
        return serviceService.listServices(PageRequest.of(page, size));
    }

    @QueryMapping
    public ServiceDTO serviceById(@Argument Long id)
    {
        return serviceService.getServiceById(id);
    }

    @MutationMapping
    public ServiceDTO addService(@Argument ServiceInput service)
    {
        return serviceService.saveService(service);
    }

    @MutationMapping
    public ServiceDTO updateService(@Argument ServiceInput service,
                                    @Argument Long id)
    {
        return serviceService.updateService(service, id);
    }

    @MutationMapping
    public Message deleteService(@Argument Long id)
    {
        serviceService.deleteService(id);
        return new Message("Сервис успешно удалён", 200,
                new Timestamp(System.currentTimeMillis()));
    }
}