package com.payconiq.stockDemo.exceptions;

public class StockNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6341383806547863180L;
	public StockNotFoundException(String exception) {
	    super(exception);
	}
}
