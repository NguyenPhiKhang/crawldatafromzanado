package com.khangse616.crawldatazanado.controllers.impl;

import com.khangse616.crawldatazanado.controllers.IProductController;
import com.khangse616.crawldatazanado.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductController implements IProductController {

    @Autowired
    private ProductService productService;

    @Override
    public String createProduct() {
        return null;
    }
}
