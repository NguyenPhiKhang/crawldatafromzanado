package com.khangse616.crawldatazanado.services;

import org.springframework.stereotype.Service;

public interface IProductService {
    String createProduct(String url);
    boolean exitsProduct(int id);
}
