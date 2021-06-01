package com.company.boatApp.View;

import com.company.boatApp.Model.Employee;

import java.util.*;

public class EmployeeView {
    public static int employeeMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------Employee Menu-----------\n" +
                "Choose an option from the following list\n" +
                "1- See all Employees\n" +
                "2- Add Employee\n" +
                "3- Update Employee's Information\n" +
                "4- Delete Employee\n" +
                "5- Go to main menu\n");
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 5)
                return userSelection;
        }
    }

    public static Map<String, String> takeEmployeeInformationFromUser() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> employeeInfo = new HashMap<>();
        System.out.print("Enter firstname: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter lastname: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter telephone number: ");
        String telephoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String emailAddress = scanner.nextLine();
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        employeeInfo.put("Firstname", firstName);
        employeeInfo.put("Lastname", lastName);
        employeeInfo.put("Telephone_Number", telephoneNumber);
        employeeInfo.put("Email_Address", emailAddress);
        employeeInfo.put("Username", userName);
        employeeInfo.put("Password", password);
        return employeeInfo;
    }

    public static List<String> takeLoginInfoFromUser() {
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tWelcome to BoatApp Login Screen\nPlease enter your username and password");
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        list.add(userName);
        list.add(password);
        return list;
    }

    public static void showReservationsLabel() {
        System.out.println("EmployeeID\t" +
                "Firstname\t" +
                "Lastname\t" +
                "Username\t\t" +
                "StartingDate");

    }

    public static void showReservations(int employeeId, String firstName, String lastName, String userName, Date startingDate) {
        System.out.println(employeeId + "\t\t\t" +
                firstName + "\t\t" +
                lastName + "\t\t" +
                userName + "\t\t\t" +
                startingDate);
    }

    public static int takeEmployeePreferenceFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a employee to update by using employeeID");
        System.out.print("Enter employeeID: ");
        int orderId = Integer.parseInt(scanner.nextLine());
        return orderId;
    }

    public static int updateMenu(String lastname) {
        Scanner scanner = new Scanner(System.in);
        System.out.format("Choose an option from following list for Mr./Ms. %s:\n" +
                "1- Change name \n" +
                "2- Change username \n" +
                "3- Change password \n" +
                "4- Back to Main Menu ", lastname);
        int userSelection = Integer.parseInt(scanner.nextLine());
        while (true) {
            if (userSelection >= 1 || userSelection <= 4)
                return userSelection;
        }

    }

    public static Map<String, String> takeEmployeNameInformationFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter firstname: ");
        String firstname = scanner.nextLine();
        System.out.print("Enter lastname: ");
        String lastname = scanner.nextLine();
        Map<String, String> map = new HashMap<>();
        map.put("Firstname", firstname);
        map.put("Lastname", lastname);
        return map;
    }

    public static String takeEmployeeUsernameFromUser() {
        System.out.print("Enter new username: ");
        return new Scanner(System.in).nextLine();
    }

    public static String takeEmployeePasswordFromUser() {
        System.out.print("Enter new password: ");
        return new Scanner(System.in).nextLine();
    }

    public static boolean takeConfirmationFromUser(String lastName) {
        while (true) {
            System.out.format("Are you sure to delete Mr/Ms%s \n?", lastName);
            System.out.print("Y/N");
            String userSelection = new Scanner(System.in).nextLine();
            if (userSelection.equalsIgnoreCase("Y")) return true;
            else if (userSelection.equalsIgnoreCase("C")) return false;
            else
                System.out.println("Invalid Selection");
        }
    }
}
