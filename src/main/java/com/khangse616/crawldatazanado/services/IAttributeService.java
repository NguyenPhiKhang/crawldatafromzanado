package com.khangse616.crawldatazanado.services;

import com.khangse616.crawldatazanado.models.Attribute;
import com.khangse616.crawldatazanado.models.Brand;

public interface IAttributeService {
    boolean existAttributeById(int id);
    Attribute save(Attribute attribute);
    Attribute findAttributeById(int id);
}
