package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.CatalogProductInteger;
import com.khangse616.crawldatazanado.repositories.CatalogProductIntegerRepository;
import com.khangse616.crawldatazanado.repositories.CatalogProductVarcharRepository;
import com.khangse616.crawldatazanado.services.ICatalogProductIntegerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogProductIntegerService implements ICatalogProductIntegerService {
    @Autowired
    private CatalogProductIntegerRepository catalogProductIntegerRepository;

    @Override
    public CatalogProductInteger save(CatalogProductInteger catalogProductInteger) {
        return null;
    }
}
