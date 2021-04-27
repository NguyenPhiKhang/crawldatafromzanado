package com.khangse616.crawldatazanado.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "option_product_varchar")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OptionProductVarchar implements Serializable {

    @Id
    private int id;
    @Column(name="value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id")
    @JsonIgnore
    private Attribute attribute;

    @ManyToMany(targetEntity = Product.class, mappedBy = "optionProductVarchars", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public OptionProductVarchar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
