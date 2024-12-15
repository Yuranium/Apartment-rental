package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.Apartment;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>
{
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"images", "services", "user"})
    Optional<Apartment> findById(Long id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"images"})
    @Query("FROM Apartment a WHERE a.roomAvailable IS TRUE")
    List<Apartment> findAll();

    @EntityGraph(value = "apartment-image-graph")
    List<Apartment> findAllById(Iterable<Long> ids);
}