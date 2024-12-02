package ru.yuriy.propertyrental.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.repositories.ServiceRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceApService
{
    private final ServiceRepository serviceRepository;

    public List<ru.yuriy.propertyrental.models.entity.Service> getAllServices()
    {
        return serviceRepository.findAll();
    }
}
