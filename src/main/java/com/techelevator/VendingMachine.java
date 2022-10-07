package com.techelevator;

import com.techelevator.exceptions.IllegalCodeException;
import com.techelevator.exceptions.NotEnoughMoneyException;
import com.techelevator.exceptions.ProductOutOfStockException;
import com.techelevator.products.*;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {

	private Map<Product, Integer> supplyMap = new HashMap<>();
	private BigDecimal balance = new BigDecimal("0.00");


	private ReportLog reportLog;

	public VendingMachine() {

		stockMachine(new File("vendingmachine.csv"));
		reportLog = new ReportLog(supplyMap);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	private void stockMachine(File file) {
		try (Scanner fileScanner = new Scanner(file)) {
			while (fileScanner.hasNextLine()) {
				String[] productInfo = fileScanner.nextLine().split("\\|");
				if (productInfo[3].equals("Chip")) {
					Product product = new Chip(productInfo[1],new BigDecimal(productInfo[2]), productInfo[0]);
					supplyMap.put(product, 5);
				} else if (productInfo[3].equals("Candy")) {
					Product product = new Candy(productInfo[1],new BigDecimal(productInfo[2]), productInfo[0]);
					supplyMap.put(product, 5);
				} else if (productInfo[3].equals("Drink")) {
					Product product = new Drink(productInfo[1],new BigDecimal(productInfo[2]), productInfo[0]);
					supplyMap.put(product, 5);
				} else if (productInfo[3].equals("Gum")) {
					Product product = new Gum(productInfo[1],new BigDecimal(productInfo[2]), productInfo[0]);
					supplyMap.put(product, 5);
				} else {
					throw new IllegalArgumentException();
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IllegalArgumentException e) {
			System.err.println("File not formatted correctly");
		} catch (IndexOutOfBoundsException e) {
			System.err.println("File not formatted correctly");
		}
	}

	public List<String> printProducts() {
		List<String> productLines = new ArrayList<>();
		for (Map.Entry<Product,Integer> entry : supplyMap.entrySet()) {
			Product product = entry.getKey();
			String productDescription = "[" + product.getSlotLocation() + "] " + product.getName() + " $" + product.getPrice();
			if (entry.getValue() == 0) {
				productDescription += " SOLD OUT";
			}
			productLines.add(productDescription);
		}
		Collections.sort(productLines);
		return productLines;
	}

	private Product findProduct(String code) {
		String lowercaseCode = code.toLowerCase();
		for (Map.Entry<Product,Integer> entry : supplyMap.entrySet()) {
			if (entry.getKey().getSlotLocation().toLowerCase().equals(lowercaseCode)) {
				return entry.getKey();
			}
		}
		throw new IllegalCodeException(code);
	}

	public String buyProduct(String code) {
		Product productToBuy = findProduct(code);
		int supplyLeft = supplyMap.get(productToBuy);
		if (supplyLeft < 1) {
			throw new ProductOutOfStockException();
		} else if (balance.compareTo(productToBuy.getPrice()) == -1) {
			throw new NotEnoughMoneyException(balance, productToBuy.getPrice());
		}
		supplyMap.put(productToBuy, supplyLeft - 1);
		balance = balance.subtract(productToBuy.getPrice());
		reportLog.updateReport(productToBuy);
		reportLog.logToFile(productToBuy.getName() + " " + code.toUpperCase(), productToBuy.getPrice(), balance);
		return productToBuy.getMessage();
	}

	public void feedMoney(BigDecimal money) {
		balance = balance.add(money);
		reportLog.logToFile("FEED MONEY:", money, balance);
	}

	public Change finishTransaction() {
		BigDecimal oldBalance = balance;
		Change change = new Change(balance);
		balance = BigDecimal.valueOf(0.00);

		reportLog.logToFile("GIVE CHANGE:", oldBalance, balance);
		return change;
	}

	public void getReportLog() {
		reportLog.getReport();
	}
}
