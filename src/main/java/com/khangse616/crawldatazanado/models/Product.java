package com.khangse616.crawldatazanado.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "sku")
    private String sku;
    @Column(name = "description")
    @JsonIgnore
    private String description;
    @Column(name = "short_description")
    @JsonIgnore
    private String shortDescription;
    @Column(name="highlight")
    private String highlight;
    @Column(name = "type_id")
    private String typeId;
    @Column(name = "visibility")
    private boolean visibility;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "promotion_percent")
    private int promotionPercent;
    @Column(name = "order_count")
    private int orderCount;
    @Column(name = "is_free_ship")
    private boolean freeShip;
    @Column(name="material")
    private String material;
    @Column(name="style")
    private String style;
    @Column(name="purpose")
    private String purpose;
    @Column(name="suitable_season")
    private String suitableSeason;
    @Column(name="made_in")
    private String madeIn;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "rating_star_id")
//    @JsonIgnore
//    private RatingStar ratingStar;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;


    @ManyToMany(targetEntity = OptionProductVarchar.class, cascade = CascadeType.ALL )
    @JoinTable(
            name = "catalog_product_varchar",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<OptionProductVarchar> optionProductVarchars = new HashSet<>();

    @ManyToMany(targetEntity = OptionProductInteger.class, cascade = CascadeType.ALL )
    @JoinTable(
            name = "catalog_product_int",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<OptionProductInteger> optionProductIntegers = new HashSet<>();

    @ManyToMany(targetEntity = OptionProductDecimal.class, cascade = CascadeType.ALL )
    @JoinTable(
            name = "catalog_product_decimal",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<OptionProductDecimal> optionProductDecimals = new HashSet<>();

    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.ALL )
    @JoinTable(
            name = "catalog_product_link",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "link_product_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Product> productLinks = new HashSet<>();

    @ManyToMany(targetEntity = Product.class, mappedBy = "productLinks", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

//    @ManyToMany(targetEntity = Image.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinTable(
//            name = "images_products",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
//
//    private Set<Image> images = new HashSet<>();

//    @ManyToMany(targetEntity = Option.class, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "option_product",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
//    @JsonIgnore
//    private Set<Option> options;

//    @OneToMany(targetEntity = Comment.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Comment> comments;
//
//    @OneToMany(targetEntity = Rating.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Rating> ratings;

//    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="img_url", unique= true, referencedColumnName = "id")
//    private Image imgUrl;

    public Product() {
    }

//    public void removeImage(Image image){
//        this.images.remove(image);
//        image.getProducts().remove(this);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPromotionPercent() {
        return promotionPercent;
    }

    public void setPromotionPercent(int promotionPercent) {
        this.promotionPercent = promotionPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFreeShip() {
        return freeShip;
    }

    public void setFreeShip(boolean freeShip) {
        this.freeShip = freeShip;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSuitableSeason() {
        return suitableSeason;
    }

    public void setSuitableSeason(String suitableSeason) {
        this.suitableSeason = suitableSeason;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<OptionProductVarchar> getOptionProductVarchars() {
        return optionProductVarchars;
    }

    public void setOptionProductVarchars(Set<OptionProductVarchar> optionProductVarchars) {
        this.optionProductVarchars = optionProductVarchars;
    }

    public Set<OptionProductInteger> getOptionProductIntegers() {
        return optionProductIntegers;
    }

    public void setOptionProductIntegers(Set<OptionProductInteger> optionProductIntegers) {
        this.optionProductIntegers = optionProductIntegers;
    }

    public Set<OptionProductDecimal> getOptionProductDecimals() {
        return optionProductDecimals;
    }

    public void setOptionProductDecimals(Set<OptionProductDecimal> optionProductDecimals) {
        this.optionProductDecimals = optionProductDecimals;
    }

    public Set<Product> getProductLinks() {
        return productLinks;
    }

    public void setProductLinks(Set<Product> productLinks) {
        this.productLinks = productLinks;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}

