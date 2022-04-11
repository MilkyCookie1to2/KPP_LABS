package com.bsuir.kpp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void testRNG_badRequest_wrongParam() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/less?number=-1")).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void testLessRNG_successResult() {
		int value = 20;
		int actual = generationController.generateLess(value).getValue();
		assertTrue(actual <= value);
	}

	@Test
	public void testMoreRNG_successResult() {
		int value = 20;
		int actual = generationController.generateMore(value).getValue();
		assertTrue(actual >= value);
	}

}
