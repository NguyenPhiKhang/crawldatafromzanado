package com.khangse616.crawldatazanado.repositories;

import com.khangse616.crawldatazanado.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
