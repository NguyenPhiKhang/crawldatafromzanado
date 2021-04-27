package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.CatalogProductDecimal;
import com.khangse616.crawldatazanado.repositories.CatalogProductDecimalRepository;
import com.khangse616.crawldatazanado.repositories.CatalogProductVarcharRepository;
import com.khangse616.crawldatazanado.services.ICatalogProductIDecimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogProductDecimalService implements ICatalogProductIDecimalService {
    @Autowired
    private CatalogProductDecimalRepository catalogProductDecimalRepository;

    @Override
    public CatalogProductDecimal save(CatalogProductDecimal catalogProductDecimal) {
        return null;
    }
}
