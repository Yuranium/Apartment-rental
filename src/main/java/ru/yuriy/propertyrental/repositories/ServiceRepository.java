package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {}