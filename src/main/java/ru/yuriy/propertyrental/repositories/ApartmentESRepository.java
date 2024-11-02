package ru.yuriy.propertyrental.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.yuriy.propertyrental.models.entity.ApartmentES;

public interface ApartmentESRepository extends ElasticsearchRepository<ApartmentES, Long> {}