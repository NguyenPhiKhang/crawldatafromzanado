package com.khangse616.crawldatazanado.controllers;

import com.khangse616.crawldatazanado.models.DTO.InputCreateProductDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface IProductController {
    String createProduct(InputCreateProductDTO input);
}
