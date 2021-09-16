package com.arnoldgalovics.batchingpractice.service;

import com.arnoldgalovics.batchingpractice.domain.Product;
import com.arnoldgalovics.batchingpractice.domain.ProductReview;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * A simple Spring component that acts as a layer on top of the pure database operations.
 */
@Service
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates products in the database.
     *
     * @param newProducts the products to be created
     */
    @Transactional
    public void create(Collection<Product> newProducts) {
        for (Product product : newProducts) {
            entityManager.persist(product);
        }
    }

    /**
     * Creates products in the database with the specified batch size.
     *
     * @param newProducts the products to be created
     * @param batchSize   the batch size that needs to be applied for this transaction
     */
    @Transactional
    public void createInBathes(Collection<Product> newProducts, int batchSize) {
        entityManager.unwrap(Session.class).setJdbcBatchSize(batchSize);
        for (Product product : newProducts) {
            entityManager.persist(product);
        }
    }

    /**
     * Resets the stock value to 0 for the specified products identified by their ids.
     *
     * @param productIds the ids of the products
     */
    @Transactional
    public void resetStock(Collection<Integer> productIds) {
        TypedQuery<Product> productQuery = entityManager.createQuery("FROM Product p WHERE p.id IN (:productIds)", Product.class);
        productQuery.setParameter("productIds", productIds);
        List<Product> products = productQuery.getResultList();
        for (Product product : products) {
            product.setStock(0);
        }
    }

    /**
     * Decreases the stock value by one for the specified products and decrements the first rating for the products.
     *
     * @param productIds the ids of the products
     */
    @Transactional
    public void decreaseStockAndDecreaseRating(Collection<Integer> productIds) {
        TypedQuery<Product> productQuery = entityManager.createQuery("FROM Product p JOIN FETCH p.reviews WHERE p.id IN (:productIds)", Product.class);
        productQuery.setParameter("productIds", productIds);
        List<Product> products = productQuery.getResultList();
        for (Product product : products) {
            product.setStock(product.getStock() - 1);
            ProductReview firstReview = product.getReviews().get(0);
            firstReview.setRating(firstReview.getRating() - 1);
        }
    }
}
