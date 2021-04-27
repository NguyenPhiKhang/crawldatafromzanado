package com.khangse616.crawldatazanado.services;

import com.khangse616.crawldatazanado.models.OptionProductDecimal;

public interface IOptionProductDecimalService {
    boolean existOptionProductDecimalById(int id);
    OptionProductDecimal save(OptionProductDecimal optionProductDecimal);
}
