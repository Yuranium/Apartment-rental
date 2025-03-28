package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.dto.ServiceDTO;
import ru.yuriy.propertyrental.models.graphql.input.ServiceInput;
import ru.yuriy.propertyrental.repositories.ServiceRepository;
import ru.yuriy.propertyrental.util.exceptions.ServiceNotFoundException;
import ru.yuriy.propertyrental.util.mappers.ServiceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRestService
{
    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    public List<ServiceDTO> listServices(Pageable pageable)
    {
        return serviceMapper.toDTOList(
                serviceRepository.findAll(pageable).getContent()
        );
    }

    public ServiceDTO getServiceById(Long id)
    {
        return serviceMapper.toDTO(
                serviceRepository.findById(id)
                        .orElseThrow(() -> new ServiceNotFoundException(
                                String.format("Сервис в id=%d не был найден", id)
                        ))
        );
    }

    public ServiceDTO saveService(ServiceInput service)
    {
        return serviceMapper.toDTO(
                serviceRepository.save(
                        serviceMapper.toEntity(service)
                )
        );
    }

    public ServiceDTO updateService(ServiceInput service, Long id)
    {
        if (serviceRepository.findById(id).isPresent())
        {
            ru.yuriy.propertyrental.models.entity.Service sv = serviceMapper.toEntity(service);
            sv.setId(id);
            return serviceMapper.toDTO(
                    serviceRepository.save(sv)
            );
        }
        else throw new ServiceNotFoundException(
                String.format("Сервис с id=%d не найден", id));
    }

    public void deleteService(Long id)
    {
        serviceRepository.deleteById(id);
    }
}