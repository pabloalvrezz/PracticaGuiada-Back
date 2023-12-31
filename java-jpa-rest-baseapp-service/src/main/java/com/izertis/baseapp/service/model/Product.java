package com.izertis.baseapp.service.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.izertis.baseapp.service.model.User.Columns;
import com.izertis.libraries.audit.jpa.model.Auditable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Product")
@Table(name = "Product")

@Getter
@Setter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Columns.ID)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Stock")
    private int stock;

    @Column(name = "price")
    private double activePrice;

    @Column(name = Columns.ENABLED)
    private boolean enabled;

    @Column(name = "Url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo")
    private Type tipo;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Prices> prices = new ArrayList<Prices>();

    @JsonIgnore
    @ManyToMany(mappedBy = "favourites")
    private List<User> users = new ArrayList<User>();

    public void addPrice(Prices price) {
        prices.add(price);
        price.setProduct(this);

    }

    public void removePrice(Prices price) {
        prices.remove(price);
        price.setProduct(null);
    }

    public void addUsers(User user) {
        users.add(user);
        user.getFavourites().add(this);
    }

    public void removeUsers(User user) {
        users.remove(user);
        user.getFavourites().remove(this);
    }

}
