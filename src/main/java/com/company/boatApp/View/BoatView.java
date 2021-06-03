package com.company.boatApp.View;

import com.company.boatApp.Model.*;

import java.util.ArrayList;
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
                "4- Back to Main Menu ");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 4)
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
                                 double remainCharge) {

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

    public static int addBoatMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------Add Boat Menu-----------\n" +
                "Choose an option from following list:\n" +
                "1- KAJAK \n" +
                "2- SUPBOARD \n" +
                "3- ROWINGBOAT \n" +
                "4- ELECTRICALBOAT\n" +
                "5- Back to Main Menu ");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 5)
                return userSelection;
        }
    }

    public static List<String> takeUserSelectionToAddBoat(int userSelection) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        System.out.print("Enter the seat number of the boat: ");
        String boatSeats = (scanner.nextLine());
        System.out.print("Enter the boat's minimum price per hour: ");
        String minimumPricePerHour = (scanner.nextLine());
        list.add(boatSeats);
        list.add(minimumPricePerHour);
        if (userSelection == 4) {
            System.out.print("Enter the charging time:");
            String chargingTime = scanner.nextLine();
            System.out.print("Enter the charge life: ");
            String chargeLife = scanner.nextLine();
            list.add(chargingTime);
            list.add(chargeLife);
        }
        return list;
    }

    public static boolean takeConfirmationFromUserToCreateBoat(BoatType boatType, List<String> boatInfo) {
        Scanner scanner = new Scanner(System.in);
        System.out.format("Boat Information:\n Boat Type: %s\nBoat Seats: %s\nMinimum Price: %s\n" +
                "to confirm press Y, to cancel press C: ", boatType, boatInfo.get(0), boatInfo.get(1));
        String userSelection = scanner.nextLine();
        if (userSelection.equalsIgnoreCase("Y")) {
            System.out.println("Boat is created!");
            return true;
        } else if (userSelection.equalsIgnoreCase("C")) {
            System.out.println("Canceled!");
            return false;
        }
        return false;
    }

    public static int boatUpdateMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------Add Boat Menu-----------\n" +
                "Choose an option from following list:\n" +
                "1- Change number of seats\n" +
                "2- Change minimum price per hour\n" +
                "3- Change charging time\n" +
                "4- Delete boat\n" +
                "5- Back to Main Menu ");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 5)
                return userSelection;
        }
    }

    public static int takeSeatsNumberPreferenceFromUser() {
        System.out.print("Enter the new seats number: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public static int takeBoatSelectionPrefernceFromUser() {
        System.out.print("Select a boat by using boatId: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public static double takeMinimumPriceFromUser() {
        System.out.print("Enter the new minimum price per hour: ");
        return Double.parseDouble(new Scanner(System.in).nextLine());
    }

    public static int takeChargingTimeFromUser() {
        System.out.print("Enter the new charging time: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public static boolean takeConfirmationFromUserToDeleteBaot(Boat boat) {
        System.out.format("Are you sure to want to delete this boat:\n" +
                "Boat Id: %s\n" +
                "Boat Type: %s", boat.getBoatId(), boat.getType()
        );
        System.out.println("to confirm press Y, to cancel press C: ");
        String userSelection = new Scanner(System.in).nextLine();
        if (userSelection.equalsIgnoreCase("Y")) {
            System.out.println("Boat is deleted!");
            return true;
        } else if (userSelection.equalsIgnoreCase("C")) {
            System.out.println("Canceled!");
            return false;
        }
        return false;
    }
}
