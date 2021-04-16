package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
