package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, String> {
}
