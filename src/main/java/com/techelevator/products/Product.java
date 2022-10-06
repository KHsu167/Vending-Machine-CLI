package com.techelevator.products;

import java.math.BigDecimal;

public abstract class Product {
    private String name;
    private BigDecimal price;
    private String slotLocation;

    public Product(String name, BigDecimal price, String slotLocation) {
        this.name = name;
        this.price = price;
        this.slotLocation = slotLocation;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public abstract String getMessage();
}
