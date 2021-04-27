package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.OptionProductInteger;
import com.khangse616.crawldatazanado.repositories.OptionProductIntegerRepository;
import com.khangse616.crawldatazanado.services.IOptionProductIntegerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionProductIntegerService implements IOptionProductIntegerService {
    @Autowired
    private OptionProductIntegerRepository optionProductIntegerRepository;

    @Override
    public boolean existOptionProductIntegerById(int id) {
        return optionProductIntegerRepository.existsById(id);
    }

    @Override
    public OptionProductInteger save(OptionProductInteger optionProductInteger) {
        return optionProductIntegerRepository.save(optionProductInteger);
    }
}
