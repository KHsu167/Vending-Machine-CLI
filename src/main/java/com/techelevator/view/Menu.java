package com.techelevator.view;

import com.techelevator.exceptions.IllegalCodeException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public String getChoiceFromOptions(String[] options) {
		String choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	public BigDecimal getMoneyFromUser() {
		BigDecimal choice = null;
		while (choice == null) {
			out.print("Enter amount of money >>> ");
			out.flush();
			try {
				choice = new BigDecimal(in.nextLine());
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid input");
			}
		}
		return choice;
	}

	public String getCodeFromUser() {
		String choice = null;
		while (choice == null) {
			out.print("Enter the code >>> ");
			out.flush();
			try {
				choice = in.nextLine();
			} catch (IllegalCodeException e) {
				System.out.println(e.getMessage());
			}
		}
		return choice;
	}

	private String getChoiceFromUserInput(String[] options) {
		String choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			} else if (selectedOption == 4) {
				choice = "Report";
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(String[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
}
