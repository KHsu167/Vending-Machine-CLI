package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineApp {


    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_SECRET_REPORT = "Report";

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    private Menu menu;
    private VendingMachine vendingMachine;

    public static void main(String[] args) {
        VendingMachineApp app = new VendingMachineApp(new Menu(System.in, System.out), new VendingMachine());
        app.run();

    }

    public VendingMachineApp(Menu menu, VendingMachine vendingMachine) {
        this.menu = menu;
        this.vendingMachine = vendingMachine;
    }

    public void run() {
        while (true) {
            String input = menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (input.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                menu.displayProducts(vendingMachine.printProducts());
            } else if (input.equals(MAIN_MENU_OPTION_PURCHASE)) {
                menu.displayMessage(String.format("Current Money Provided: $%.2f", vendingMachine.getBalance()));
                input = menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
                if (input.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                    vendingMachine.feedMoney(menu.getMoneyFromUser());
                } else if (input.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                    try {
                        menu.displayMessage(vendingMachine.buyProduct(menu.getCodeFromUser()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (input.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                    menu.displayMessage(vendingMachine.finishTransaction().getMessage());
                }
            } else if (input.equals(MAIN_MENU_OPTION_EXIT)) {
                return;
            } else if (input.equals(MAIN_MENU_OPTION_SECRET_REPORT)) {
                vendingMachine.getReportLog();
            }
        }
    }
}
