package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.Brand;
import com.khangse616.crawldatazanado.services.IBrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandService implements IBrandService {
    @Override
    public boolean existBrandById(int id) {
        return false;
    }

    @Override
    public Brand save(Brand brand) {
        return null;
    }

    @Override
    public Brand findBrandById(int id) {
        return null;
    }


//    @Autowired
//    private BrandRepository brandRepository;
//
//    @Override
//    public boolean existBrandById(int id) {
//        return brandRepository.existsById(id);
//    }
//
//    @Override
//    public Brand save(Brand brand) {
//        return brandRepository.save(brand);
//    }
//
//    @Override
//    public Brand findBrandById(int id) {
//        return brandRepository.findById(id).get();
//    }
}
