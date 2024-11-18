package ru.yuriy.propertyrental.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.repositories.ApartmentESRepository;
import ru.yuriy.propertyrental.repositories.ApartmentRepository;

import java.util.stream.Collectors;


@Configuration
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig
{
    private final ApartmentRepository apartmentRepository;

    private final ApartmentESRepository apartmentESRepository;

    @Async
    @Scheduled(cron = "@hourly")
    public void migrationDataToElastic()
    {
        apartmentESRepository.saveAll(
                apartmentRepository.findAll().stream()
                        .map(ap -> {
                            ApartmentSearch apartment = new ApartmentSearch();
                            apartment.setId(ap.getId());
                            apartment.setName(ap.getName());
                            apartment.setRoomCount(ap.getRoomCount());
                            apartment.setDailyPrice(ap.getDailyPrice());
                            apartment.setApartmentType(apartment.getApartmentType());
                            return apartment;
                        })
                        .collect(Collectors.toList())
        );
    }
}