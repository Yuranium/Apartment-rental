package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.util.ApartmentMapper;
import ru.yuriy.propertyrental.util.exceptions.ApartmentNotFoundException;

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

    @Transactional(readOnly = true)
    public ApartmentDTO getApartment(Long id)
    {
        return apartmentMapper.toDTO(apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentNotFoundException(
                        String.format("Апартамент с id=%d не найден", id)
                )));
    }

    @Transactional
    public void addApartment(ApartmentDTO apartment)
    {
        apartmentRepository.save(
                apartmentMapper.toEntity(apartment));
    }
}