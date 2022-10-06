package com.techelevator.products;

import java.math.BigDecimal;

public class Drink extends Product {
    public Drink(String name, BigDecimal price, String slotLocation) {
        super(name, price, slotLocation);
    }

    @Override
    public String getMessage() {
        return "Glug Glug, Yum!";
    }
}