package ru.yuriy.propertyrental.perositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuriy.propertyrental.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
