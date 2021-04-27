package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.CatalogProductVarchar;
import com.khangse616.crawldatazanado.repositories.CatalogProductVarcharRepository;
import com.khangse616.crawldatazanado.services.ICatalogProductVarcharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogProductVarcharService implements ICatalogProductVarcharService {
    @Autowired
    private CatalogProductVarcharRepository catalogProductVarcharRepository;

    @Override
    public CatalogProductVarchar save(CatalogProductVarchar catalogProductVarchar) {
        return catalogProductVarcharRepository.save(catalogProductVarchar);
    }
}
