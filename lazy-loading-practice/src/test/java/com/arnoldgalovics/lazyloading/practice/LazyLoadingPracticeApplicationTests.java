package com.arnoldgalovics.lazyloading.practice;

import com.arnoldgalovics.lazyloading.practice.domain.ProductReview;
import com.arnoldgalovics.lazyloading.practice.service.ProductService;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LazyLoadingPracticeApplicationTests {
	@Autowired
	private ProductService productService;

	@Test
	public void testLoadingReviewsForProductWorks() {
		// dataset is initialized in main/resources/data.sql
		int productId = 1;
		// Since there are 3 reviews saved to the database for product id 1 in data.sql
		int expectedReviewCount = 3;

		// load the actual reviews through the service
		Collection<ProductReview> reviews = productService.getReviewsForProduct(productId);
		int reviewCount = reviews.size();

		// verify the review count available
		assertEquals(expectedReviewCount, reviewCount);
	}

	@Test
	public void testLoadingAverageRatingForProductIsEfficient() {
		// clear up query counts for verification
		QueryCountHolder.clear();
		// dataset is initialized in main/resources/data.sql
		int expectedAverage = 4;
		int productId = 1;

		// calculate the average for the product
		int average = productService.getAverageRatingForProduct(productId);

		// verify that the average lines up with the expected one
		assertEquals(expectedAverage, average);
		// verify that only a single select query is executed
		assertEquals(1, QueryCountHolder.getGrandTotal().getSelect());
	}

	@Test
	public void testLoadingOverallAverageRatingIsEfficient() {
		// clear up query counts for verification
		QueryCountHolder.clear();
		// dataset is initialized in main/resources/data.sql
		int expectedAverage = 4;

		// calculate the overall average
		int average = productService.getOverallAverageRating();

		// verify that the average lines up with the expected one
		assertEquals(expectedAverage, average);
		// verify that only a single select query is executed
		assertEquals(1, QueryCountHolder.getGrandTotal().getSelect());
	}
}
