package com.payconiq.stockDemo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payconiq.stockDemo.dao.StockRepository;
import com.payconiq.stockDemo.dto.Stock;
import com.payconiq.stockDemo.exceptions.StockNotFoundException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class StockRestController {
	
	Logger log = LogManager.getLogger(StockRestController.class.getName());
	
	@Autowired
	StockRepository repository;
	
	@ApiOperation(value="Lists all the stocks",produces="application/json", response=List.class)
	@GetMapping("stocks")
	public List<Stock> getAllStocks() {
		return repository.findAll();
	}
	
	@ApiOperation(value="Retrieve a stock with given Id",produces="application/json",response=Stock.class)
	@GetMapping("stocks/{id}")
	public ResponseEntity<Object> getAStock(@PathVariable Integer id) {
		Optional<Stock> stock = repository.findById(id);
		if (!stock.isPresent()) {
			log.error("Unable to update stock. Stock of id " + id + " does not exist.");
			throw new StockNotFoundException("Stock with id : " + id + " is NOT FOUND");
		}
		return new ResponseEntity<>(stock.get(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Creates a stock",produces="application/json",response=Stock.class)
	@PostMapping("stocks")
	public ResponseEntity<Object> addIncomingStock(@Valid @RequestBody Stock newStock){
		Stock savedStock =repository.save(newStock);
		return new ResponseEntity<>(savedStock,HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Updates the stock price",produces="application/json",response=Stock.class)
	@PutMapping("stocks/{id}")
	public ResponseEntity<Object> updateIncomingStockPrice(@PathVariable Integer id, @Valid @RequestBody Stock newStock){
		Optional<Stock> stock =repository.findById(id);
		Stock stockToUpdate = null;
		if (stock.isPresent()) {
			stockToUpdate = stock.get();
			stockToUpdate.setCurrentPrice(newStock.getCurrentPrice());
			stockToUpdate = repository.save(stockToUpdate);
			return new ResponseEntity<>(stockToUpdate,HttpStatus.OK);
		}else{
			log.error("Unable to update stock price. Stock of id " + id + " does not exist.");
			throw new StockNotFoundException("Stock with id : " + id + " is NOT FOUND");
		}
	}
}
