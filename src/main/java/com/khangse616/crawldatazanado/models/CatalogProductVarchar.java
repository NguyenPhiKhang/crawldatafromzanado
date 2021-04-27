package com.khangse616.crawldatazanado.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "option_product_varchar")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CatalogProductVarchar implements Serializable {

    @Id
    @Column(name="product_id")
    private int productId;
    @Id
    @Column(name="option_id")
    private int optionId;

    public CatalogProductVarchar() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
