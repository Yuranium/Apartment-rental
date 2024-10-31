package ru.yuriy.propertyrental.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.ApartmentES;

@Repository
public interface ApartmentESRepository extends ElasticsearchRepository<ApartmentES, Long> {}