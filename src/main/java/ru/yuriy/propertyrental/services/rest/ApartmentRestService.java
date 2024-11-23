package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.util.ApartmentMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApartmentRestService
{
    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    @Transactional(readOnly = true)
    public List<ApartmentDTO> listApartments(PageRequest pageRequest)
    {
        return apartmentMapper.toDTOList(apartmentRepository
                .findAll(pageRequest)
                .getContent());
    }
}