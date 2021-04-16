package com.khangse616.crawldatazanado.services;


import com.khangse616.crawldatazanado.models.Attribute;
import com.khangse616.crawldatazanado.models.OptionProductVarchar;

public interface IOptionProductVarcharService {
    boolean existOptionProductVarcharById(int id);
    OptionProductVarchar save(OptionProductVarchar optionProductVarchar);
    OptionProductVarchar findOptionProductVarcharById(int id);
}
