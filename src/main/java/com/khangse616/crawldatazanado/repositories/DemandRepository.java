package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Integer> {
    Demand findDemandById(Integer id);
}
