package ru.yuriy.propertyrental.perositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {}