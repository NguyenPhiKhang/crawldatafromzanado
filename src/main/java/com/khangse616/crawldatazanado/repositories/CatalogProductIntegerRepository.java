package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.CatalogProductInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogProductIntegerRepository extends JpaRepository<CatalogProductInteger, Integer> {
}
