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
        String url = "https://www.zanado.com/bo-do-the-thao-nang-dong-colo-cl1703339-dep-gia-tot-giam-30-sid65674.html?color=77";
        return productService.createProduct(url);
    }
}
