package com.techelevator;

import com.techelevator.exceptions.IllegalCodeException;
import com.techelevator.exceptions.NotEnoughMoneyException;
import com.techelevator.exceptions.ProductOutOfStockException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineTest {

    private VendingMachine sut;

    @Before
    public void setup() {
        sut = new VendingMachine();
        sut.feedMoney(BigDecimal.valueOf(5.00));
        sut.buyProduct("a1");
    }

    @Test
    public void getBalance_return_total_after_purchasing_a1() {

        BigDecimal actual = sut.getBalance();

        Assert.assertEquals( BigDecimal.valueOf(1.95), actual);
    }

    @Test
    public void getBalance_return_total_after_feeding_3_more_dollars() {

        sut.feedMoney(BigDecimal.valueOf(3.00));
        BigDecimal actual = sut.getBalance();

        Assert.assertEquals(BigDecimal.valueOf(4.95), actual);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void buyProduct_return_Out_of_money() {

        sut.buyProduct("a1");
        sut.buyProduct("a1");
        sut.buyProduct("a1");
        sut.buyProduct("a1");

        sut.buyProduct("a1");
    }

    @Test(expected = ProductOutOfStockException.class)
    public void buyProduct_return_Out_of_stock() {

        sut.feedMoney(BigDecimal.valueOf(20));
        sut.buyProduct("a1");
        sut.buyProduct("a1");
        sut.buyProduct("a1");
        sut.buyProduct("a1");

        sut.buyProduct("a1");
    }

    @Test(expected = IllegalCodeException.class)
    public void buyProduct_return_illegal_code() {

        sut.buyProduct("z1");

    }

    @Test
    public void finishTransaction_return_7_quarters_2_dimes() {

        Change change = sut.finishTransaction();

        Assert.assertEquals(7, change.numberOfQuarters);
        Assert.assertEquals(2, change.numberOfDimes);
        Assert.assertEquals(0, change.numberOfNickels);
    }

    @Test
    public void finishTransaction_return_nothing() {

        sut.finishTransaction();
        Change change = sut.finishTransaction();

        Assert.assertEquals(0, change.numberOfQuarters);
        Assert.assertEquals(0, change.numberOfDimes);
        Assert.assertEquals(0, change.numberOfNickels);
        Assert.assertEquals("Returning: Nothing", change.getMessage());
    }
}
