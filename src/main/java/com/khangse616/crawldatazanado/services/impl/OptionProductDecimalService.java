package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.OptionProductDecimal;
import com.khangse616.crawldatazanado.repositories.OptionProductDecimalRepository;
import com.khangse616.crawldatazanado.services.IOptionProductDecimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionProductDecimalService implements IOptionProductDecimalService {
    @Autowired
    private OptionProductDecimalRepository optionProductDecimalRepository;

    @Override
    public boolean existOptionProductDecimalById(int id) {
        return optionProductDecimalRepository.existsById(id);
    }

    @Override
    public OptionProductDecimal save(OptionProductDecimal optionProductDecimal) {
        return optionProductDecimalRepository.save(optionProductDecimal);
    }
}
