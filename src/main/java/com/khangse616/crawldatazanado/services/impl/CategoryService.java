package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.repositories.CategoryRepository;
import com.khangse616.crawldatazanado.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
}
