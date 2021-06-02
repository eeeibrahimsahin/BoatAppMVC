package com.company.boatApp.View;

import com.company.boatApp.Model.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BoatView {
    public static void showBoats(Model model) {

    }

    public static int boatMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------Boat Menu-----------\n" +
                "Choose an option from following list:\n" +
                "1- See boat list \n" +
                "2- Add boat \n" +
                "3- Update boat \n" +
                "4- Delete boat\n" +
                "5- Back to Main Menu ");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 5)
                return userSelection;
        }
    }

    public static void showNotAvailableBoats(List<Boat> notAvailableBoatsAtTheMoment) {
        showBoatLabel();
        notAvailableBoatsAtTheMoment.stream().forEach(boat -> showBoats(boat.getBoatId(), boat.getType(),
                boat.getSeats(), boat.getStatus(), boat.getRemainingCharge()));
    }

    public static void showBoatLabel() {
        System.out.println(
                "BoatId\t" +
                        "BoatType\t\t\t\t" +
                        "Seats\t" +
                        "Status\t\t\t" +
                        "RemainingCharge");
    }

    public static void showBoats(int boatId, BoatType boatType, int seats, BoatStatus boatStatus,
                                 double remainCharge ) {

        if (boatType == BoatType.ELECTRICALBOAT) {
            String s = "[==========]";
            if (remainCharge < 100 && remainCharge >= 90) {
                s = "[========= ]";
            } else if (remainCharge < 90 && remainCharge >= 80) {
                s = "[========  ]";
            } else if (remainCharge < 80 && remainCharge >= 70) {
                s = "[=======   ]";
            } else if (remainCharge < 70 && remainCharge >= 60) {
                s = "[======    ]";
            } else if (remainCharge < 60 && remainCharge >= 50) {
                s = "[=====     ]";
            } else if (remainCharge < 50 && remainCharge >= 40) {
                s = "[====      ]";
            } else if (remainCharge < 40 && remainCharge >= 30) {
                s = "[===       ]";
            } else if (remainCharge < 30 && remainCharge >= 20) {
                s = "[==        ]";
            } else if (remainCharge < 20 && remainCharge >= 10) {
                s = "[=         ]";
            } else if (remainCharge < 10 && remainCharge >= 0)
                s = "[          ]";
            if (remainCharge < 0) {
                System.out.format("%3.3s\t\t" +
                        "%-20.20s\t" +
                        "%-2.2s\t\t" +
                        "%-11.11s\t\t" +
                        "%-12.12s\n", boatId, boatType, seats, boatStatus, "Charging...");
            } else {
                System.out.format("%3.3s\t\t" +
                        "%-20.20s\t" +
                        "%-2.2s\t\t" +
                        "%-11.11s\t\t" +
                        "%4.4s%%\t" +
                        "%-12.12s\n", boatId, boatType, seats, boatStatus, remainCharge, s);
            }

        } else {
            System.out.format("%3.3s\t\t" +
                    "%-20.20s\t" +
                    "%-2.2s\t\t" +
                    "%-11.11s\t\t" +
                    "%4.4s\t" +
                    "%-12.12s\n", boatId, boatType, seats, boatStatus, "", "-");

        }
    }

}
