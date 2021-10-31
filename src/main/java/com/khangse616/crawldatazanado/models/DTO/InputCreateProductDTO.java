package com.khangse616.crawldatazanado.models.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InputCreateProductDTO {
    private String url;
    private int idDemand;
    private int idCategory;
}
