package com.khangse616.crawldatazanado.services;

import com.khangse616.crawldatazanado.models.DTO.InputCreateProductDTO;
import org.springframework.stereotype.Service;

public interface IProductService {
    String createProduct(InputCreateProductDTO input);
    boolean exitsProduct(int id);
}
