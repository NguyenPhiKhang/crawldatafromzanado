package com.khangse616.crawldatazanado.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "attributes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attribute {
    @Id
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OptionProductVarchar> options;

    public Attribute(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
