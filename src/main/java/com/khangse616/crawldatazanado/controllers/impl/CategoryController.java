package com.khangse616.crawldatazanado.controllers.impl;

import com.khangse616.crawldatazanado.controllers.ICategoryController;
import com.khangse616.crawldatazanado.services.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CategoryController implements ICategoryController {
    @Autowired
    private CategoryService categoryService;
}
