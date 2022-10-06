package com.techelevator.products;

import java.math.BigDecimal;

public class Gum extends Product {
    public Gum(String name, BigDecimal price, String slotLocation) {
        super(name, price, slotLocation);
    }

    @Override
    public String getMessage() {
        return "Chew Chew, Yum!";
    }
}
