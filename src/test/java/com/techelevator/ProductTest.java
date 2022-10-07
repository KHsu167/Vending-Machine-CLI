package com.techelevator;

import com.techelevator.products.Candy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {


    @Test
    public void Candy_message_returns_munch_munch() {
        Candy candy = new Candy("Wonka Bar", new BigDecimal(1.50), "B3");
        String actual = candy.getMessage();
        String expected = "Munch Munch, Yum!";
        Assert.assertEquals(expected, actual);
    }

}
