package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.Apartment;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>
{
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"images", "services"})
    Optional<Apartment> findById(Long id);
}