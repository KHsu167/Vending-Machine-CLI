package com.techelevator.exceptions;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(BigDecimal currentMoney, BigDecimal price) {
        super("Insufficient funds: You have: $" + currentMoney + " Cost: $" + price);
    }

}