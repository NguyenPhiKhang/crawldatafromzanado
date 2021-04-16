package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.OptionProductVarchar;
import com.khangse616.crawldatazanado.repositories.OptionProductVarcharRepository;
import com.khangse616.crawldatazanado.services.IOptionProductVarcharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionProductVarcharService implements IOptionProductVarcharService {
    @Autowired
    private OptionProductVarcharRepository optionProductVarcharRepository;

    @Override
    public boolean existOptionProductVarcharById(int id) {
        return optionProductVarcharRepository.existsById(id);
    }

    @Override
    public OptionProductVarchar save(OptionProductVarchar optionProductVarchar) {
        return optionProductVarcharRepository.save(optionProductVarchar);
    }

    @Override
    public OptionProductVarchar findOptionProductVarcharById(int id) {
        return optionProductVarcharRepository.findById(id).get();
    }
}
