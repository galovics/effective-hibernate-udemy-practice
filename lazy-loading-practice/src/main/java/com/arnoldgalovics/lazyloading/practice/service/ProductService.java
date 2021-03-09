package com.arnoldgalovics.lazyloading.practice.service;

import com.arnoldgalovics.lazyloading.practice.domain.Product;
import com.arnoldgalovics.lazyloading.practice.domain.ProductReview;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
     * Loads a {@link Product} from the underlying database.
     *
     * @param productId the id of the {@link Product} to be loaded
     * @return the {@link Product} instance associated with the given productId or null in case it cannot be found
     */
    @Transactional
    public Collection<ProductReview> getReviewsForProduct(int productId) {
        Product product = entityManager.find(Product.class, productId);
        return product.getReviews();
    }

    /**
     * Calculates the average rating for a given {@link Product} based on the linked {@link ProductReview}s.
     * <br><br>
     * An average can be a floating point number but this method rather takes the integer part of the number.
     * <br>
     * Example: 13/3 = 4.33 and the integer returned is 4.
     * @param productId the id of the {@link Product}
     * @return the integer average rating
     */
    @Transactional
    public int getAverageRatingForProduct(int productId) {
        Product product = entityManager.find(Product.class, productId);
        List<ProductReview> reviews = product.getReviews();
        int sum = 0;
        for (ProductReview review : reviews) {
            sum += review.getRating();
        }
        return (int) ((double) sum / (double) reviews.size());
    }

    /**
     * Calculates the overall rating for all {@link Product}s available in the database.
     * <br><br>
     * Similar to {@link ProductService#getAverageRatingForProduct}, it only returns the integer part of the average.
     * @return the integer average rating for all the products available
     */
    @Transactional
    public int getOverallAverageRating() {
            List<Product> products = entityManager.createQuery("FROM Product", Product.class).getResultList();
        int sum = 0;
        int countOfReviews = 0;
        for (Product product : products) {
            List<ProductReview> reviews = product.getReviews();
            countOfReviews += reviews.size();
            for (ProductReview review : reviews) {
                sum += review.getRating();
            }
        }
        return (int) ((double) sum / (double) countOfReviews);
    }
}
