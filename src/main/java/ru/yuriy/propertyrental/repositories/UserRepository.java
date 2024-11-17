package ru.yuriy.propertyrental.repositories;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles"})
    List<User> findAll();

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles"})
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u JOIN FETCH u.apartments WHERE u.id = :id")
    Optional<User> findByIdTogetherApartment(Long id);

    @Transactional(readOnly = true)
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findByPhone(String phone);
}