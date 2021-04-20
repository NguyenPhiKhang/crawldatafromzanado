package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.CatalogProductVarchar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogProductVarcharRepository extends JpaRepository<CatalogProductVarchar, Integer> {
}
