package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.CatalogProductDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogProductDecimalRepository extends JpaRepository<CatalogProductDecimal, Integer> {
}
