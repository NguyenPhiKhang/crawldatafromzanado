package com.khangse616.crawldatazanado.services;

import com.khangse616.crawldatazanado.models.Category;

public interface ICategoryService {
    Category save(Category category);
    boolean existCategory(int id);
    Category findCategoryById(int id);
}
