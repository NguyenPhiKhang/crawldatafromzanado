package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.OptionProductVarchar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionProductVarcharRepository extends JpaRepository<OptionProductVarchar, Integer> {
}
