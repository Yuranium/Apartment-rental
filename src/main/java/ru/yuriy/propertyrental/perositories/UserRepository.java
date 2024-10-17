package ru.yuriy.propertyrental.perositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Transactional(readOnly = true)
    Optional<User> findByEmailOrPhone(String email, String phone);
}