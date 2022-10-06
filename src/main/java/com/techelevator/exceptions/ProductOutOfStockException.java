package com.techelevator.exceptions;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException() {
        super("OUT OF STOCK");
    }

}
