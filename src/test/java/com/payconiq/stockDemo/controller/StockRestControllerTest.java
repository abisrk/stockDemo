package com.payconiq.stockDemo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stockDemo.dao.StockRepository;
import com.payconiq.stockDemo.dto.Stock;
import com.payconiq.stockDemo.exceptions.StockNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(StockRestController.class)
class StockRestControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StockRepository repository;
		
	JacksonTester<List<Stock>> jsonLstResponse;
	JacksonTester<Stock> jsonResponse;
	
	@Test
	void testGetAllStocks()  {
		String uri = "/api/stocks";
		List<Stock> lstMockStocks = new ArrayList();
		Stock mockStock = new Stock("MSFT",500.35);
		lstMockStocks.add(mockStock);
		
		Mockito.when(repository.findAll()).thenReturn(lstMockStocks);
		
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				      .accept(MediaType.APPLICATION_JSON)).andReturn();
			
			int status = mvcResult.getResponse().getStatus();
			assertThat(status).isEqualTo(HttpStatus.OK.value());
			
			String response = mvcResult.getResponse().getContentAsString();

			JacksonTester.initFields(this, new ObjectMapper());
			String expected = jsonLstResponse.write(lstMockStocks).getJson();
			JSONAssert.assertEquals(expected, response, true);
		} catch (Exception e) {
			fail("Exception thrown when all stocks must be returned", e);
		}
	}
	
	@Test
	void testGetOneStock()  {
		String uri = "/api/stocks/5";
		Optional<Stock> mockStock = Optional.ofNullable(new Stock("MSFT",500.35));
		
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(mockStock);
		
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				      .accept(MediaType.APPLICATION_JSON)).andReturn();
			
			int status = mvcResult.getResponse().getStatus();
			assertThat(status).isEqualTo(HttpStatus.OK.value());
			
			String response = mvcResult.getResponse().getContentAsString();

			JacksonTester.initFields(this, new ObjectMapper());
			String expected = jsonResponse.write(mockStock.get()).getJson();
			JSONAssert.assertEquals(expected, response, true);
		} catch (Exception e) {
			fail("Exception thrown when all stocks must be returned", e);
		}
	}

	@Test
	void testGetNonExistingStock()  {
		String uri = "/api/stocks/175";
		Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new StockNotFoundException("Stock with id : 175 is NOT FOUND"));
		
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				      .accept(MediaType.APPLICATION_JSON)).andReturn();
			
			int status = mvcResult.getResponse().getStatus();
			assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
			
			String response = mvcResult.getResponse().getContentAsString();
			assertThat(response.contains("Stock with id : 175 is NOT FOUND"));
		} catch (Exception e) {
			fail("Exception thrown when all stocks must be returned", e);
		}
	}
	
	@Test
	void testGetThrowsUnhandledException()  {
		String uri = "/api/stocks/175";
		Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new NumberFormatException("Some UNKNOWN exception"));
		
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				      .accept(MediaType.APPLICATION_JSON)).andReturn();
			
			int status = mvcResult.getResponse().getStatus();
			assertThat(status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} catch (Exception e) {
			fail("Exception thrown when all stocks must be returned", e);
		}
	}
	
	@Test
	void testPostOneStock()  {
		String uri = "/api/stocks";
		Stock mockStock = new Stock("GHRS",34.5);
		
		Mockito.when(repository.save(Mockito.any(Stock.class))).thenReturn(mockStock);
		String exampleStock = "{\"name\": \"GHRS\",\"currentPrice\": 34.5,\"lastUpdatedAt\": \"2019-02-20 13:15:22 +0000\"}";
		
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				      	.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				      	.contentType(MediaType.APPLICATION_JSON).content(exampleStock))
						.andReturn();
			
			int status = mvcResult.getResponse().getStatus();
			assertThat(status).isEqualTo(HttpStatus.CREATED.value());
			
			String response = mvcResult.getResponse().getContentAsString();

			JacksonTester.initFields(this, new ObjectMapper());
			String expected = jsonResponse.write(mockStock).getJson();
			JSONAssert.assertEquals(expected, response, true);
		} catch (Exception e) {
			fail("Exception thrown when all stocks must be returned", e);
		}
	}
}
