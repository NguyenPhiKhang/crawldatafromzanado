package com.khangse616.crawldatazanado.services;

import com.khangse616.crawldatazanado.models.OptionProductInteger;
import com.khangse616.crawldatazanado.models.OptionProductVarchar;

public interface IOptionProductIntegerService {
    boolean existOptionProductIntegerById(int id);
    OptionProductInteger save(OptionProductInteger optionProductInteger);
}
