package com.company.boatApp.View;

import com.company.boatApp.Model.Boat;
import com.company.boatApp.Model.BoatType;
import com.company.boatApp.Model.Order;

import java.util.*;

public class OrderView {
    public static int orderMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------Reservation Menu-----------\n" +
                "Choose an option from the following list\n" +
                "1- See all reservations\n" +
                "2- Make reservation\n" +
                "3- Change reservation\n" +
                "4- Delete reservation\n" +
                "5- Go to main menu\n");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 5)
                return userSelection;
        }
    }

    public static int orderUpdateMenu(int orderId) {
        Scanner scanner = new Scanner(System.in);
        System.out.format("Choose an option from following list for number of %s:\n" +
                "1- Change date of the reservation\n" +
                "2- Change client information of the reservation\n" +
                "3- Change the boat\n" +
                "4- Back to Main Menu", orderId);
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 4)
                return userSelection;
        }
    }

    public static int reportMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option from following list: \n" +
                "1- Daily Report\n" +
                "2- Weekly Report\n" +
                "3- Monthly Report\n" +
                "4- Yearly Report\n" +
                "5- Back to Main Menu");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 5)
                return userSelection;
        }
    }

    public static String takeDatePreferenceFromUserToCreateReport(String reportName) {
        System.out.println("Date format should be that yyyy-MM-dd");
        System.out.format("Enter the desired report date to create %s report: ", reportName);
        return new Scanner(System.in).nextLine();
    }

    public static String takePreferencesFromUserAboutTourDate() {
        System.out.println("Date format should be that yyyy-MM-dd HH:mm:ss");
        System.out.print("Enter the new date: ");
        return new Scanner(System.in).nextLine();

    }

    public static void showReservationsLabel() {
        System.out.println("OrderId\t" +
                "BoatId\t" +
                "BoatType\t\t\t" +
                "Tour Date\t\t\t\t\t" +
                "Client\t\t" +
                "Employee\t");
    }

    public static void showReservations(int orderId, int boatId, BoatType boatType, Date tourDate, String clientName
            , String employee) {
        System.out.format("%3.3s\t\t" +
                "%3.3s\t\t" +
                "%-10.10s\t" +
                "%-30.30s\t\t" +
                "%-10.10s\t" +
                "%-10.10s\n",orderId,boatId,boatType,tourDate,clientName,employee);
    }

    public static List<String> takePreferencesFromUserAboutTour() {
        List list = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Date format should be that yyyy-MM-dd HH:mm:ss");
        System.out.print("Enter desired reservation date to see available boats: ");
        String resDate = scanner.nextLine();
        System.out.print("Enter desired tour duration: ");
        String rentingDuration = scanner.nextLine();
        list.add(resDate);
        list.add(rentingDuration);
        return list;
    }

    public static int takeBoatPreferenceFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter selected boat id: ");
        int boatId = Integer.parseInt(scanner.nextLine());
        return boatId;
    }

    public static void showAvailableBoats(List<Boat> availableBoatList) {
        System.out.println("Available boat info:\n id - Type - Capacity");
        availableBoatList.stream().forEach(boat2 -> System.out.println(boat2.getBoatId() + "\t " +
                boat2.getType() + "\t " + boat2.getSeats()));
    }

    public static boolean takeReservationConfirmationFromUser(String resDate, BoatType boatType, String clientFirstname,
                                                              String clientLastname, double totalPrice) {
        Scanner scanner = new Scanner(System.in);

        System.out.format("Order Information:\n " +
                        "Reservation Date: %s\n " +
                        "Boat Type: %s\n " +
                        "Client Name: %s\n " +
                        "Total Price: %.2f Euro\n" +
                        "to confirm pres Y, to cancel press C: ", resDate, boatType,
                clientFirstname + " " + clientLastname, totalPrice);
        String userSelection = scanner.nextLine();
        while (true) {
            if (userSelection.equalsIgnoreCase("Y"))
                return true;
            else if (userSelection.equalsIgnoreCase("C"))
                return false;
        }
    }

    public static int takePreferencesFromUserToUpdateAReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a reservation to update by using reservationID");
        System.out.print("Enter reservationId: ");
        int orderId = Integer.parseInt(scanner.nextLine());
        return orderId;
    }

    public static void showReport(List<Order> orderList) {
        double totalPrice = orderList.stream().map(order -> order.getTotalPrice()).reduce(0.0, Double::sum);
        System.out.println("OrderID\t\t" +
                "Boat Type\t\t" +
                "Duration\t\t" +
                "Price (Euro)");
        orderList.stream().forEach(order -> System.out.format("%3.3s\t\t\t" +
                "%-10.10s\t\t" +
                "%-2.2shour\t\t\t" +
                "%-5.5s\n",order.getOrderId(),
                order.getBoat().getType(),
                order.getRentingDuration(),
                order.getTotalPrice()));
        System.out.println("--------------------------------------------------------------");
        System.out.format("Total Price: %.2f Euro\n", totalPrice);
        System.out.println("---------------------------------------------------------------");
    }
}
