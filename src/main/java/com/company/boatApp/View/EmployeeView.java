package com.company.boatApp.View;

import com.company.boatApp.Model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeView {
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

}
