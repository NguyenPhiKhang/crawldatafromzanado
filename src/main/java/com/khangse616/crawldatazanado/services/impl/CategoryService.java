package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.Category;
import com.khangse616.crawldatazanado.services.ICategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public boolean existCategory(int id) {
        return false;
    }

    @Override
    public Category findCategoryById(int id) {
        return null;
    }
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Override
//    public Category save(Category category) {
//        return categoryRepository.save(category);
//    }
//
//    @Override
//    public boolean existCategory(int id) {
//        return categoryRepository.existsCategoryById(id);
//    }
//
//    @Override
//    public Category findCategoryById(int id) {
//        return categoryRepository.findCategoryById(id);
//    }

}
