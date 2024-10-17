package ru.yuriy.propertyrental.perositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.entity.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Transactional(readOnly = true)
    Optional<User> findByEmailOrPhone(String email, String phone);

    @Transactional(readOnly = true)
    Optional<User> findByPhoneAndEmail(String phone, String email);
}