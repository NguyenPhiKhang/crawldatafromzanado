package com.khangse616.crawldatazanado.controllers.impl;

import com.khangse616.crawldatazanado.controllers.IProductController;
import com.khangse616.crawldatazanado.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductController implements IProductController {

    @Autowired
    private ProductService productService;

    @Override
    @PostMapping("/create-product")
    public String createProduct() {
        String url = "https://www.zanado.com/ao-khoac-nam-akuba-cao-cap-3309-dep-gia-tot-sid66310.html?color=52";
        return productService.createProduct(url);
    }
}
