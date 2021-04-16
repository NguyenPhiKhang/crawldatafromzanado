package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}
