package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.OptionProductInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionProductIntegerRepository extends JpaRepository<OptionProductInteger, Integer> {
}
