package com.company.boatApp.View;

import java.util.Scanner;

public class MainMenuView {
    public static int mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------Main Menu-----------\n" +
                "Choose an option from the following list\n" +
                "1- Reservation\n" +
                "2- Customer\n" +
                "3- Boat\n" +
                "4- Employee\n" +
                "5- Reports\n" +
                "6- Save\n" +
                "7- Go to login screen\n" +
                "8- Exit app");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 8)
                return userSelection;
        }
    }

    public static boolean getConfirmationFromUser() {
        System.out.println("Do you want to save all these changes? if your answer is yes then press Y\n" +
                "to cancel press C");
        while (true) {
            String userSelection = new Scanner(System.in).nextLine();
            if (userSelection.equalsIgnoreCase("S")) return true;
            else if (userSelection.equalsIgnoreCase("C")) return false;
            else System.out.println("Invalid selection");
        }
    }
}
