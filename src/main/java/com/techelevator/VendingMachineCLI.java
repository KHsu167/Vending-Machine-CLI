package com.techelevator;

import com.techelevator.exceptions.IllegalCodeException;
import com.techelevator.exceptions.NotEnoughMoneyException;
import com.techelevator.exceptions.ProductOutOfStockException;
import com.techelevator.products.*;
import com.techelevator.view.Menu;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachineCLI {

	private Map<Product, Integer> supplyMap = new HashMap<>();
	private BigDecimal balance = new BigDecimal("0.00");

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY,PURCHASE_MENU_OPTION_SELECT_PRODUCT,PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		stockMachine(new File("vendingmachine.csv"));


		feedMoney(BigDecimal.valueOf(5.00));

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

	public void printProducts() {
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
		for (String line : productLines) {
			System.out.println(line);
		}
	}

	private Product findProduct(String code) {
		for (Map.Entry<Product,Integer> entry : supplyMap.entrySet()) {
			if (entry.getKey().getSlotLocation().equals(code)) {
				return entry.getKey();
			}
		}
		throw new IllegalCodeException(code);
	}

	public void buyProduct(String code) {
		Product productToBuy = findProduct(code);
		int supplyLeft = supplyMap.get(productToBuy);
		if (supplyLeft < 1) {
			throw new ProductOutOfStockException();
		} else if (balance.compareTo(productToBuy.getPrice()) == -1) {
			throw new NotEnoughMoneyException(balance, productToBuy.getPrice());
		}
		supplyMap.put(productToBuy, supplyLeft - 1);
		balance = balance.subtract(productToBuy.getPrice());
		System.out.println(productToBuy.getMessage());

		logToFile(productToBuy.getName() + " " + code, productToBuy.getPrice(), balance);
	}

	public void feedMoney(BigDecimal money) {
		balance = balance.add(money);
		logToFile("FEED MONEY:", money, balance);
	}

	public void finishTransaction() {
		BigDecimal oldBalance = balance;
		int numberOfQuarters = 0;
		int numberOfDimes = 0;
		int numberOfNickels = 0;
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
		logToFile("GIVE CHANGE:", oldBalance, balance);

		System.out.printf("Returning %d Quarter(s) %d Dime(s) %d Nickel(s)", numberOfQuarters, numberOfDimes, numberOfNickels);
	}

	public void logToFile(String description, BigDecimal amount, BigDecimal balance) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
		String dateAndTime = formatter.format(now);
		try (PrintWriter writer = new PrintWriter(new FileWriter("Log.txt", true))) {
//			writer.printf(dateAndTime + " " + description + " $" + amount + " $" + balance);
			writer.printf("%s %s $%.2f $%.2f", dateAndTime, description, amount, balance);
		} catch (IOException e) {
			System.err.println("File Not Found");
		}
	}

	public void run() {

		/*
		  ===== you nay use/modify the existing Menu class or write your own ======
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			}
		}
		 */
	}

}
