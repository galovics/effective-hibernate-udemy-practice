package com.arnoldgalovics.entitytransitions.practice.service;

import com.arnoldgalovics.entitytransitions.practice.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * A simple Spring component that acts as a layer on top of the pure database operations.
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * Loads a {@link Product} from the underlying database.
     * @param id the id of the {@link Product} to be loaded
     * @return the {@link Product} instance associated with the given id or null in case it cannot be found
     */
    public Product find(int id) {
        return productRepository.find(id);
    }

    /**
     * Buys a product by id decreasing the amount of stock available.
     * @param id the id of the product
     * @param amountToBuy the amount to buy
     */
    public void buy(int id, int amountToBuy) {
        Product product = find(id);
        product.setStock(product.getStock() - amountToBuy);
        productRepository.save(product);
    }

    /**
     * Creates a new product with the specified productName and initialStock.
     * @param productName the name of the product
     * @param initialStock the initial stock of the product
     * @return the id of the created product
     */
    public int create(String productName, int initialStock) {
        int id = new Random().nextInt();
        Product product = new Product(id, productName, initialStock);
        productRepository.save(product);
        return id;
    }
}
