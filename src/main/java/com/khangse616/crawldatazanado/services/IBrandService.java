package com.khangse616.crawldatazanado.services;

import com.khangse616.crawldatazanado.models.Brand;

public interface IBrandService {
    boolean existBrandById(int id);
    Brand save(Brand brand);
}
