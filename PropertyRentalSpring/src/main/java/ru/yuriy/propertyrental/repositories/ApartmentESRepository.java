package ru.yuriy.propertyrental.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.yuriy.propertyrental.models.ApartmentSearch;

public interface ApartmentESRepository extends ElasticsearchRepository<ApartmentSearch, Long> {}