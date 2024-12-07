package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.dto.ApartmentDTO;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.models.graphql.input.ApartmentInput;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;
import ru.yuriy.propertyrental.util.exceptions.ApartmentNotFoundException;
import ru.yuriy.propertyrental.util.mappers.ApartmentMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "apartment_rest")
public class ApartmentRestService
{
    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    private final ApplicationContext applicationContext;

    @Transactional(readOnly = true)
    public List<ApartmentDTO> listApartments(PageRequest pageRequest)
    {
        return apartmentMapper.toDTOList(apartmentRepository
                .findAll(pageRequest)
                .getContent());
    }

    @Cacheable(key = "#id")
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

    @Transactional
    @CachePut(key = "#id")
    public void updateApartment(ApartmentDTO apartment, Long id)
    {
        Apartment ap = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentNotFoundException(
                        String.format("Апартамент с id=%d не найден", id)));
        ap.setName(apartment.name());
        ap.setSquare(apartment.square());
        ap.setRoomCount(apartment.roomCount());
        ap.setDailyPrice(apartment.dailyPrice());
        ap.setType(apartment.type());
        ap.setAddress(apartment.address());
        ap.setRoomAvailable(apartment.roomAvailable());
        apartmentRepository.save(ap);
    }

    @Transactional
    @CacheEvict(key = "#id")
    public void deleteApartment(Long id)
    {
        apartmentRepository.deleteById(id);
    }

    public ApartmentDTO saveApartment(ApartmentInput apartment)
    {
        return apartmentMapper.toDTO(
                apartmentRepository.save(
                        apartmentMapper.toEntity(apartment)
                )
        );
    }

    @Transactional
    @CachePut(key = "#id")
    public ApartmentDTO updateApartment(ApartmentInput apartment, Long id)
    {
        if (apartmentRepository.findById(id).isPresent())
        {
            Apartment ap = apartmentMapper.toEntity(apartment);
            ap.setId(id);
            return apartmentMapper.toDTO(
                    apartmentRepository.save(ap)
            );
        }
        else throw new ApartmentNotFoundException(
                String.format("Апартамент с id=%d не найден", id));
    }

    @CachePut(key = "#id", unless = "#result == null")
    @Transactional(readOnly = true)
    public ApartmentDTO cache(Long id)
    {
        CacheManager cacheManager = (CacheManager) applicationContext.getBean("cacheManager");
        return apartmentMapper.toDTO(
                apartmentRepository.findById(id)
                        .orElseThrow(ApartmentNotFoundException::new)
        );
    }
}