package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.DataImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataImageRepository extends JpaRepository<DataImage, String> {
    boolean existsDataImageById(String id);
}
