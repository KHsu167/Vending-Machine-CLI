package com.techelevator;

import java.math.BigDecimal;

public class Change {

    int numberOfQuarters = 0;
    int numberOfDimes = 0;
    int numberOfNickels = 0;

    public int getNumberOfQuarters() {
        return numberOfQuarters;
    }

    public int getNumberOfDimes() {
        return numberOfDimes;
    }

    public int getNumberOfNickels() {
        return numberOfNickels;
    }

    public Change(BigDecimal balance) {
        while (balance.compareTo(BigDecimal.valueOf(0.25)) != -1) {
            balance = balance.subtract(BigDecimal.valueOf(0.25));
            numberOfQuarters++;
        }
        while (balance.compareTo(BigDecimal.valueOf(0.10)) != -1) {
            balance = balance.subtract(BigDecimal.valueOf(0.10));
            numberOfDimes++;
        }
        while (balance.compareTo(BigDecimal.valueOf(0.05)) != -1) {
            balance = balance.subtract(BigDecimal.valueOf(0.05));
            numberOfNickels++;
        }


    }

    public String getMessage() {
        String result = "Returning: ";
        if (numberOfQuarters != 0) {
            if (numberOfQuarters > 1) {
                result += numberOfQuarters + " Quarters ";
            } else {
                result += numberOfQuarters + " Quarter ";
            }

        }
        if (numberOfDimes != 0) {
            if (numberOfDimes > 1) {
                result += numberOfDimes + " Dimes ";
            } else {
                result += numberOfDimes + " Dime ";
            }
        }
        if (numberOfNickels == 1) {
            result += numberOfNickels + " Nickel";
        }
        if (numberOfQuarters == 0 && numberOfDimes == 0 && numberOfNickels == 0) {
            result += "Nothing";
        }

        if (result.lastIndexOf(" ") == result.length() - 1) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
