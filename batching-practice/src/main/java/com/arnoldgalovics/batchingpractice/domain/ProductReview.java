package com.arnoldgalovics.batchingpractice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An attachment entity for {@link Product}s that holds rating information.
 */
@Entity
@Table(name = "product_reviews")
public class ProductReview {
    @Id
    private int id;
    private int rating;

    @ManyToOne
    private Product product;

    private ProductReview() {
    }

    public ProductReview(int id, int rating) {
        this.id = id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
