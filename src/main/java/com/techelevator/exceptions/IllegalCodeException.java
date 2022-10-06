package com.techelevator.exceptions;

public class IllegalCodeException extends RuntimeException {
    public IllegalCodeException(String code) {
        super("Invalid code: " + code);
    }

}
