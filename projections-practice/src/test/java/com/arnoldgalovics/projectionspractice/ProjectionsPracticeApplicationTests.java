package com.arnoldgalovics.projectionspractice;

import com.arnoldgalovics.projectionspractice.service.ProductService;
import com.arnoldgalovics.projectionspractice.support.LastQueryHolder;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectionsPracticeApplicationTests {
	@Autowired
	private ProductService productService;

	@Test
	public void testGetProductNamesIsOnlyLoadingNecessaryData() {
		// clear up query counts for verification
		QueryCountHolder.clear();
		// dataset is initialized in main/resources/data.sql
		Collection<String> expectedProductNames = Arrays.asList("Red Shirt", "T-Shirt");

		// calculate the overall average
		Collection<String> productNames = productService.getProductNames();

		// verify that the product names are equal with the expected one
		assertThat(productNames).containsExactlyInAnyOrderElementsOf(expectedProductNames);
		// verify that only the necessary information is queried back
		assertThat(LastQueryHolder.lastSelectQuery()).containsExactlyInAnyOrder("name");
	}
}
