package com.arnoldgalovics.projectionspractice.service;


import com.arnoldgalovics.projectionspractice.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple Spring component that acts as a layer on top of the pure database operations.
 */
@Service
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * The method returns all the product names available in the underlying database.
     * @return the collection of product names
     */
    @Transactional
    public Collection<String> getProductNames() {
        List<Product> products = entityManager.createQuery("FROM Product", Product.class).getResultList();
        return products.stream().map(Product::getName).collect(Collectors.toList());
    }
}
