package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.Attribute;
import com.khangse616.crawldatazanado.repositories.AttributeRepository;
import com.khangse616.crawldatazanado.services.IAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeService implements IAttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public boolean existAttributeById(int id) {
        return attributeRepository.existsById(id);
    }

    @Override
    public Attribute save(Attribute brand) {
        return attributeRepository.save(brand);
    }

    @Override
    public Attribute findAttributeById(int id) {
        return attributeRepository.findById(id).get();
    }
}
