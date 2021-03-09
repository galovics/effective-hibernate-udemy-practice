package com.arnoldgalovics.batchingpractice;

import com.arnoldgalovics.batchingpractice.domain.Company;
import com.arnoldgalovics.batchingpractice.domain.Product;
import com.arnoldgalovics.batchingpractice.domain.ProductReview;
import com.arnoldgalovics.batchingpractice.service.CompanyService;
import com.arnoldgalovics.batchingpractice.service.ProductService;
import com.arnoldgalovics.batchingpractice.support.Options;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BatchingPracticeApplicationTests {
    @Autowired
    private Options options;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompanyService companyService;

    /**
     * The test case verifies if the global statement batching is working.
     */
    @Test
    public void testGeneralInsertBatchingIsWorking() {
        // clear up query counts for verification
        QueryCountHolder.clear();

        // creating the Product entities
        Collection<Product> newProducts = createProducts(5);

        // calling the service to persist the new products to the database
        productService.create(newProducts);

        // verify that only a single insert query is executed
        assertEquals(1, QueryCountHolder.getGrandTotal().getInsert());
    }

    /**
     * The test case verifies if transaction level batching is used in the underlying implementation.
     */
    @Test
    public void testSessionLevelInsertBatchingIsWorking() {
        // clear up query counts for verification
        QueryCountHolder.clear();

        int globalBatchSize = options.globalBatchSize();

        // creating the Product entities
        Collection<Product> newProducts = createProducts(6 * globalBatchSize);

        // calling the service to persist the new products to the database
        productService.createInBathes(newProducts, 3 * globalBatchSize);

        // verify that only a single insert query is executed
        assertEquals(2, QueryCountHolder.getGrandTotal().getInsert());
    }

    /**
     * The test case verifies if the general batching is working for update statements.
     */
    @Test
    public void testGeneralUpdateBatchingIsWorking() {
        // creating the Product entities
        Collection<Product> newProducts = createProducts(5);
        Collection<Integer> productIds = newProducts.stream().map(Product::getId).collect(Collectors.toList());

        // calling the service to persist the new products to the database
        productService.create(newProducts);
        // clear up query counts for verification
        QueryCountHolder.clear();

        // calling the service to reset the stock values to zero
        productService.resetStock(productIds);

        // verify that only a single update query is executed
        assertEquals(1, QueryCountHolder.getGrandTotal().getUpdate());
    }

    /**
     * The tst case verifies that insert reordering is in place so batching is still happening in case of
     * different entity type inserts.
     */
    @Test
    public void testInsertReorderingIsWorking() {
        // clear up query counts for verification
        QueryCountHolder.clear();
        // creating the Product entities
        Collection<Product> newProducts = createProducts(5);
        attachReviews(newProducts);

        // calling the service to persist the new products to the database
        productService.create(newProducts);

        // verify that only 2 insert query is executed
        assertEquals(2, QueryCountHolder.getGrandTotal().getInsert());
    }

    /**
     * The tst case verifies that update reordering is in place so batching is still happening in case of
     * different entity type updates.
     */
    @Test
    public void testUpdateReorderingIsWorking() {
        // creating the Product entities
        Collection<Product> newProducts = createProducts(5);
        attachReviews(newProducts);
        Collection<Integer> productIds = newProducts.stream().map(Product::getId).collect(Collectors.toList());

        // calling the service to persist the new products to the database
        productService.create(newProducts);

        // clear up query counts for verification
        QueryCountHolder.clear();

        // calling the service to decrement the stock and decrease the rating
        productService.decreaseStockAndDecreaseRating(productIds);

        // verify that only 2 update query is executed
        assertEquals(2, QueryCountHolder.getGrandTotal().getUpdate());
    }

    /**
     * The test case verifies that batching is working for versioned entities.
     */
    @Test
    public void testVersionedBatchingIsWorking() {
        // creating the Company entities
        Collection<Company> newCompanies = createCompanies(5);
        Collection<Integer> companyIds = newCompanies.stream().map(Company::getId).collect(Collectors.toList());

        // calling the service to persist the new companies to the database
        companyService.create(newCompanies);

        // clear up query counts for verification
        QueryCountHolder.clear();

        // calling the service to reset the product counts
        companyService.resetProductCounts(companyIds);

        // verify that only a single update query is executed
        assertEquals(1, QueryCountHolder.getGrandTotal().getUpdate());
    }

    private void attachReviews(Collection<Product> newProducts) {
        for (Product product : newProducts) {
            int reviewId = product.getId() * 10;
            ProductReview review = new ProductReview(reviewId, 5);
            product.getReviews().add(review);
            review.setProduct(product);
        }
    }

    private Collection<Product> createProducts(int count) {
        Random r = new Random();
        Collection<Product> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(new Product(i, "Product" + i, r.nextInt(20)));
        }
        return result;
    }

    private Collection<Company> createCompanies(int count) {
        Random r = new Random();
        Collection<Company> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(new Company(i, r.nextInt(20)));
        }
        return result;
    }

}
