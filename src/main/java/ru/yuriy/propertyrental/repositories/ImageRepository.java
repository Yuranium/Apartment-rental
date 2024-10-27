package ru.yuriy.propertyrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yuriy.propertyrental.models.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {}