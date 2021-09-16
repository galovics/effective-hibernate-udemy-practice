package com.arnoldgalovics.projectionspractice.service;


import com.arnoldgalovics.projectionspractice.domain.Product;
import com.arnoldgalovics.projectionspractice.domain.ProductName;
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
        List<ProductName> productNames = entityManager.createQuery("SELECT NEW " +
                "com.arnoldgalovics.projectionspractice.domain.ProductName(p.name) FROM Product p",
                ProductName.class).getResultList();
        return productNames.stream().map(ProductName::getName).collect(Collectors.toList());
    }
}
