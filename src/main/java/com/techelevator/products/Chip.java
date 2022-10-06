package com.techelevator.products;

import java.math.BigDecimal;

public class Chip extends Product {

    public Chip(String name, BigDecimal price, String slotLocation) {
        super(name, price, slotLocation);
    }

    @Override
    public String getMessage() {
        return "Crunch Crunch, Yum!";
    }
}
