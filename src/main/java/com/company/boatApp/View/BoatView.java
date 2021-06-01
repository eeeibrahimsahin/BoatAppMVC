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
        System.out.println("Choose an option from following list:\n" +
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

    public static void showBoats(int boatId, BoatType boatType, int seats, BoatStatus boatStatus, double remainCharging) {

        if (boatType == BoatType.ELECTRICALBOAT) {
            String s = "[==========]";
            if (remainCharging < 100 && remainCharging >= 90) {
                s = "[========= ]";
            } else if (remainCharging < 90 && remainCharging >= 80) {
                s = "[========  ]";
            } else if (remainCharging < 80 && remainCharging >= 70) {
                s = "[=======   ]";
            } else if (remainCharging < 70 && remainCharging >= 60) {
                s = "[======    ]";
            } else if (remainCharging < 60 && remainCharging >= 50) {
                s = "[=====     ]";
            } else if (remainCharging < 50 && remainCharging >= 40) {
                s = "[====      ]";
            } else if (remainCharging < 40 && remainCharging >= 30) {
                s = "[===       ]";
            } else if (remainCharging < 30 && remainCharging >= 20) {
                s = "[==        ]";
            } else if (remainCharging < 20 && remainCharging >= 10) {
                s = "[=         ]";
            } else if (remainCharging < 10 && remainCharging >= 0)
                s = "[          ]";
            System.out.format("%3.3s\t\t" +
                    "%-20.20s\t" +
                    "%-2.2s\t\t" +
                    "%-11.11s\t\t" +
                    "%4.4s%%\t" +
                    "%-12.12s\n", boatId, boatType, seats, boatStatus, remainCharging, s);
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
