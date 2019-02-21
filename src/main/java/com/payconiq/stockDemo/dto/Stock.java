package com.payconiq.stockDemo.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
public class Stock {

	@Id @GeneratedValue
	int id;
	
	@NotNull
	@Size(min=2, message="Name should have atleast 2 characters")
	String name;
	
	@PositiveOrZero(message="Price should be positive or Zero")
	double currentPrice;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss z")
	Date lastUpdatedAt;
	
	public Stock() {
		super();
	}

	public Stock(String name, double currentPrice) {		
		this(name,currentPrice,new Date());
	}
	
	public Stock(String name, double currentPrice, Date lastUpdatedAt) {
		super();
		this.name = name;
		this.currentPrice = currentPrice;
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	
	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", currentPrice=" + currentPrice + ", lastUpdatedAt="
				+ lastUpdatedAt + "]";
	}	
}
