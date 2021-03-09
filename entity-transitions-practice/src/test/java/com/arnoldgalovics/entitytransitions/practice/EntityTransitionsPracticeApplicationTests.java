package com.arnoldgalovics.entitytransitions.practice;

import com.arnoldgalovics.entitytransitions.practice.domain.Product;
import com.arnoldgalovics.entitytransitions.practice.service.ProductService;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EntityTransitionsPracticeApplicationTests {
	@Autowired
	private ProductService productService;

	/**
	 * This test case emulates that a product has been bought by a customer and the necessary state
	 * modifications have been made in the database.
	 */
	@Test
	public void testBuyingProductByIdWorks() {
		// clear up query counts for verification
		QueryCountHolder.clear();
		// dataset is initialized in main/resources/data.sql
		// calculate the expected amount in advance
		Product tShirtProduct = productService.find(1);
		int stockToBuy = 5;
		int expectedStock = tShirtProduct.getStock() - stockToBuy;

		// buying 5 of product with id 1
		productService.buy(1, stockToBuy);

		// verify that the stock equals to the expected amount
		int stock = productService.find(1).getStock();
		assertEquals(expectedStock, stock);
		// verify that there were 3 select and 1 update statement issued
		assertEquals(3, QueryCountHolder.getGrandTotal().getSelect());
		assertEquals(1, QueryCountHolder.getGrandTotal().getUpdate());
	}

	/**
	 * The test case verifies that a new {@link Product} entity is properly saved to the database
	 * with the least amount of queries
	 */
	@Test
	public void testCreateProductWorks() {
		// clear up query counts for verification
		QueryCountHolder.clear();
		String productName = "Red Shirt";
		int initialStock = 4;

		// creating a new product
		int productId = productService.create(productName, initialStock);

		// verify that product is saved
		Product newProduct = productService.find(productId);
		assertEquals(productName, newProduct.getName());
		assertEquals(initialStock, newProduct.getStock());
		// verify that there were 1 select and 1 insert statement issued
		assertEquals(1, QueryCountHolder.getGrandTotal().getSelect());
		assertEquals(1, QueryCountHolder.getGrandTotal().getInsert());
	}
}
