package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.OptionProductDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionProductDecimalRepository extends JpaRepository<OptionProductDecimal, Integer> {
}
