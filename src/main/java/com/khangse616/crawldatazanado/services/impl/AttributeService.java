package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.Attribute;
import com.khangse616.crawldatazanado.services.IAttributeService;
import org.springframework.stereotype.Service;

@Service
public class AttributeService implements IAttributeService {
    @Override
    public boolean existAttributeById(int id) {
        return false;
    }

    @Override
    public Attribute save(Attribute attribute) {
        return null;
    }

    @Override
    public Attribute findAttributeById(int id) {
        return null;
    }

//    @Autowired
//    private AttributeRepository attributeRepository;
//
//    @Override
//    public boolean existAttributeById(int id) {
//        return attributeRepository.existsById(id);
//    }
//
//    @Override
//    public Attribute save(Attribute attribute) {
//        return attributeRepository.save(attribute);
//    }
//
//    @Override
//    public Attribute findAttributeById(int id) {
//        return attributeRepository.findById(id).get();
//    }
}
