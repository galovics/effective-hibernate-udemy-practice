package com.arnoldgalovics.batchingpractice.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A very simple representation of a Product that is used for demonstration purposes.
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    private int id;
    private String name;
    private int stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> reviews = new ArrayList<>();

    private Product() {
    }

    public Product(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public List<ProductReview> getReviews() {
        return reviews;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", reviews=" + reviews +
                '}';
    }
}
