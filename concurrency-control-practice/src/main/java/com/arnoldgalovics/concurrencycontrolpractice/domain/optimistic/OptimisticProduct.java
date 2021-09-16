package com.arnoldgalovics.concurrencycontrolpractice.domain.optimistic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A very simple representation of a Product that is used for demonstration purposes.
 */
@Entity
@Table(name = "opt_products")
public class OptimisticProduct {
    @Id
    private int id;
    private String name;
    private int stock;
    @Version
    private int version;

    private OptimisticProduct() {
    }

    public OptimisticProduct(int id, String name, int stock) {
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

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }
}
