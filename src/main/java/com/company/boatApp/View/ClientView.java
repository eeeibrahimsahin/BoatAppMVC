package com.company.boatApp.View;

import com.company.boatApp.Model.BoatType;
import com.company.boatApp.Model.Client;
import com.company.boatApp.Model.Model;

import java.util.*;

public class ClientView {
    public static Map<String, String> takeClientInformationFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Client Firstname: ");
        String clientFirstname = scanner.nextLine();
        System.out.print("Enter Client Lastname: ");
        String clientLastname = scanner.nextLine();
        System.out.print("Enter Client telephone number: ");
        String clientTelephoneNumber = (scanner.nextLine());
        System.out.print("Enter Client address: ");
        String clientAddress = scanner.nextLine();
        System.out.print("Enter Client email address: ");
        String clientEmailAddress = scanner.nextLine();

        Map<String, String> info = new HashMap<>();
        info.put("Firstname", clientFirstname);
        info.put("Lastname", clientLastname);
        info.put("TelephoneNumber", clientTelephoneNumber);
        info.put("Address", clientAddress);
        info.put("EmailAddress", clientEmailAddress);
        return info;
    }


    public static int clientMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option from following list for number of:\n" +
                "1- Customer List\n" +
                "2- Change customer information\n" +
                "3- Delete customer\n" +
                "4- Back to Main Menu");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 4)
                return userSelection;
        }
    }

    public static void showClients(Model model) {
        System.out.println("ClientId\t"+
                "Name\t\t\t\t\t" +
                "Address\t\t\t\t\t\t" +
                "TelephoneNumber");
        model.clientList.stream().forEach(client -> System.out.format("%3.3s\t\t\t" +
                "%-10.10s " +
                "%-10.10s\t" +
                "%-30.30s" +
                "%-10.10s\n" ,client.getClientId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAddress(),
                client.getTelephoneNumber()));
    }

    public static int takeUserPreferenceForChoosingClient(Model model){
        System.out.print("Choose a customer by using clientId: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
}
