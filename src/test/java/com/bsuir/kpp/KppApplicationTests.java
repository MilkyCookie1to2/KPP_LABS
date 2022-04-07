package com.bsuir.kpp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
class KppApplicationTests {

	@Autowired
	GenerationController generationController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(generationController).build();
	}

	@Test
	public void testRNG_successfulResult_correctRequest() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/less?number=90")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		//with HttpStatus.OK doesn't work, cuz return 200 instead 200 OK
	}

	@Test
	public void testRNG_badRequest_wrongParam() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/less?number=-1")).andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
		//with HttpStatus.BAD_REQUEST doesn't work cuz return 400 instead 400 BAD_REQUEST
	}

	@Test
	public void testLessRNG_SuccessResult() {
		int value = 20;
		int actual = generationController.generateLess(value).getValue();
		int expected = value;
		assertTrue(actual <= expected);
	}

	@Test
	public void testMoreRNG_SuccessResult() {
		int value = 20;
		int actual = generationController.generateMore(value).getValue();
		int expected = value;
		assertTrue(actual >= expected);
	}

}
