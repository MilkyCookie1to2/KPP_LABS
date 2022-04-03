package com.bsuir.kpp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KppApplicationTests {

	@Test
	void testLess() {
		int number = 20;
		boolean result = false;
		if((int) (Math.random() * (++number))<number)
			result = true;
		assertTrue(result);
	}

	@Test
	void testMore() {
		int number = 20;
		boolean result = false;
		if((int) (Math.random() * 100 + number)>number)
			result = true;
		assertTrue(result);
	}

}
