package com.techelevator.products;

import java.math.BigDecimal;

public class Candy extends Product {
    public Candy(String name, BigDecimal price, String slotLocation) {
        super(name, price, slotLocation);
    }

    @Override
    public String getMessage() {
        return "Munch Munch, Yum!";
    }
}
