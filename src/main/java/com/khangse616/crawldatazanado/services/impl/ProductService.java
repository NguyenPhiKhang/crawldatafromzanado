package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.repositories.ProductRepository;
import com.khangse616.crawldatazanado.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public String createProduct() {
        return null;
    }
}
