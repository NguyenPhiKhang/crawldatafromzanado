package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.Brand;
import com.khangse616.crawldatazanado.models.Category;
import com.khangse616.crawldatazanado.repositories.BrandRepository;
import com.khangse616.crawldatazanado.services.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandService implements IBrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public boolean existBrandById(int id) {
        return brandRepository.existsById(id);
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }
}
