package com.khangse616.crawldatazanado.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "option_product_decimal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OptionProductDecimal implements Serializable {

    @Id
    private int id;
    @Column(name="value")
    private BigDecimal value;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "attribute_id")
//    @JsonIgnore
//    private Attribute attribute;

    @Column(name = "attribute_id")
    private int attribute;

    @ManyToMany(targetEntity = Product.class, mappedBy = "optionProductDecimals", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public OptionProductDecimal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

//    public Attribute getAttribute() {
//        return attribute;
//    }
//
//    public void setAttribute(Attribute attribute) {
//        this.attribute = attribute;
//    }


    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
