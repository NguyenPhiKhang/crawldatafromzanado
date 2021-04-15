package com.khangse616.crawldatazanado.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "data_images")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// 3 size: 255x298 - 360x420 - 700x817
public class DataImage implements Serializable {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "data")
    @JsonIgnore
    private byte[] data;

    @Column(name="link")
    private String link;

//    @ManyToMany(targetEntity = Product.class, mappedBy = "images", cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
//    @JsonIgnore
//    private Set<Product> products;

//    @OneToOne(targetEntity = Product.class, mappedBy = "imgUrl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Product product;

    public DataImage(){}

    public DataImage(String id, String name, String type, byte[] data) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public DataImage(String id, String name, String type, String link) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

//    public Set<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Product> products) {
//        this.products = products;
//    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
}
