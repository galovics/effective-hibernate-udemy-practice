package com.arnoldgalovics.batchingpractice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A very simple representation of a Company that is used for demonstration purposes.
 */
@Entity
@Table(name = "companies")
public class Company {
    @Id
    private int id;
    private int productCount;
    @Version
    private int version;

    private Company() {
    }

    public Company(int id, int productCount) {
        this.id = id;
        this.productCount = productCount;
    }

    public int getId() {
        return id;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", productCount=" + productCount +
                '}';
    }
}
