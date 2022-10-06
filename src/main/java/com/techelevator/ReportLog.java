package com.techelevator;

import com.techelevator.products.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReportLog {
    private Map<Product, Integer> purchaseMap = new HashMap<>();

    public ReportLog(Map<Product, Integer> map) {
        initializeReport(map);
    }

    public void updateReport(Product product) {
        purchaseMap.put(product, purchaseMap.get(product) + 1);
    }

    public void getReport() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy_hh-mm-ss-a");
        String dateAndTime = formatter.format(now);
        BigDecimal totalSales = new BigDecimal("0.00");
        try (PrintWriter writer = new PrintWriter(dateAndTime + "-Report.txt")) {
            for (Map.Entry<Product,Integer> entry : purchaseMap.entrySet()) {
                writer.println(entry.getKey().getName() + "|" + entry.getValue());
                totalSales = totalSales.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
            }
            writer.println();
            writer.printf("**TOTAL SALES** $%.2f%n", totalSales);

        } catch (IOException e) {
            System.err.println("File not found");
            System.err.println(e.getMessage());
        }

    }

    public void initializeReport(Map<Product, Integer> supplyMap) {
        for (Map.Entry<Product,Integer> entry : supplyMap.entrySet()) {
            purchaseMap.put(entry.getKey(), 0);
        }
    }

    public void logToFile(String description, BigDecimal amount, BigDecimal balance) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        String dateAndTime = formatter.format(now);
        try (PrintWriter writer = new PrintWriter(new FileWriter("Log.txt", true))) {
            writer.printf("%s %s $%.2f $%.2f%n", dateAndTime, description, amount, balance);
        } catch (IOException e) {
            System.err.println("File Not Found");
        }
    }
}
