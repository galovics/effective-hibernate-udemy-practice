package com.arnoldgalovics.concurrencycontrolpractice;

import com.arnoldgalovics.concurrencycontrolpractice.domain.optimistic.OptimisticDistinctProduct;
import com.arnoldgalovics.concurrencycontrolpractice.domain.optimistic.OptimisticProduct;
import com.arnoldgalovics.concurrencycontrolpractice.support.TransactionalRunner;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OptimisticLockingTest {
    @Autowired
    private TransactionalRunner txRunner;

    /**
     * Test case to verify that when modifying the OptimisticProduct entity's same attribute, it results in
     * optimistic locking exception.
     */
    @Test
    public void testOptimisticLockingWorks() {
        // Saving a new product to the database
        int pId = 1;
        txRunner.doInTransaction(em -> {
            em.persist(new OptimisticProduct(pId, "T-Shirt", 10));
        });

        Throwable exception = catchThrowable(() -> {
            // Starting one transaction to change the product's name
            txRunner.doInTransaction(em1 -> {
                OptimisticProduct p1 = em1.find(OptimisticProduct.class, pId);
                // Starting another transaction while the previous one is active, simulating two concurrent
                // transactions and changing the product's name
                txRunner.doInTransaction(em2 -> {
                    OptimisticProduct p2 = em2.find(OptimisticProduct.class, pId);
                    p2.setName("Tablet");
                });
                p1.setName("Phone");
            });
        });
        // expecting an ObjectOptimisticLockingFailureException exception to be thrown
        assertNotNull(exception);
        assertEquals(ObjectOptimisticLockingFailureException.class, exception.getClass());
    }

    /**
     * Test case to verify that in case the same set of attributes are modified on the same ObjectDistinctProduct
     * entity, optimistic locking exception is thrown.
     */
    @Test
    public void testDistinctAttributeOptimisticLockingHappensWhenSameAttributeIsModified() {
        // Saving a new product to the database
        int pId = 1;
        txRunner.doInTransaction(em -> {
            em.persist(new OptimisticDistinctProduct(pId, "T-Shirt", 10));
        });

        Throwable exception = catchThrowable(() -> {
            // Starting one transaction to change the product's name
            txRunner.doInTransaction(em1 -> {
                OptimisticDistinctProduct p1 = em1.find(OptimisticDistinctProduct.class, pId);
                // Starting another transaction while the previous one is active, simulating two concurrent
                // transactions and changing the product's name
                txRunner.doInTransaction(em2 -> {
                    OptimisticDistinctProduct p2 = em2.find(OptimisticDistinctProduct.class, pId);
                    p2.setName("Tablet");
                });
                p1.setName("Phone");
            });
        });
        // expecting an ObjectOptimisticLockingFailureException exception to be thrown
        assertNotNull(exception);
        assertEquals(ObjectOptimisticLockingFailureException.class, exception.getClass());
    }

    /**
     * Test case to verify that in case distinct attributes are modified on the same ObjectDistinctProduct
     * entity, there is no optimistic lock exception is thrown.
     */
    @Test
    public void testDistinctAttributeOptimisticLockingDoesntHappenWhenDistinctAttributesAreModified() {
        // Saving a new product to the database
        int pId = 1;
        txRunner.doInTransaction(em -> {
            em.persist(new OptimisticDistinctProduct(pId, "T-Shirt", 10));
        });

        // Starting one transaction to change the product's name
        txRunner.doInTransaction(em1 -> {
            OptimisticDistinctProduct p1 = em1.find(OptimisticDistinctProduct.class, pId);
            // Starting another transaction while the previous one is active, simulating two concurrent
            // transactions and changing the product's stock
            txRunner.doInTransaction(em2 -> {
                OptimisticDistinctProduct p2 = em2.find(OptimisticDistinctProduct.class, pId);
                p2.setStock(2);
            });
            p1.setName("Phone");
        });
    }
}
