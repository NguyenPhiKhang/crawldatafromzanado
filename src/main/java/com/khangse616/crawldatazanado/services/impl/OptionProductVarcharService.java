package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.repositories.OptionProductVarcharRepository;
import com.khangse616.crawldatazanado.services.IOptionProductVarcharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionProductVarcharService implements IOptionProductVarcharService {
    @Autowired
    private OptionProductVarcharRepository optionProductVarcharRepository;

}
