package com.company.boatApp.Controller;

import com.company.boatApp.Model.Employee;
import com.company.boatApp.Model.Model;
import com.company.boatApp.View.ClientView;
import com.company.boatApp.View.EmployeeView;
import com.company.boatApp.View.MainMenuView;
import com.company.boatApp.View.OrderView;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class MainController {
    public static void start(Model model, ObjectMapper mapper) throws ParseException, IOException {
        Employee employee = new Employee();
        MAIN:
        while (true) {
            while (true) {
                if ((employee = EmployeeController.login(model)) != null) break;
            }
            MAINMENU:
            while (true) {
                System.out.println("Employer: " + employee.getFirstName() + " " + employee.getLastName());
                int userSelection = MainMenuView.mainMenu();
                if (userSelection == 1) {
                    while (true) {
                        int userSelectionFromOrderMenu = OrderView.orderMenu();
                        if (userSelectionFromOrderMenu == 6) break;
                        OrderController.execute(userSelectionFromOrderMenu, employee, model);
                    }
                } else if (userSelection == 2) {
                    while (true) {
                        int userSelectionFromClientMenu = ClientView.clientMenu();
                        if (userSelectionFromClientMenu == 4) break;
                        ClientController.execute(userSelectionFromClientMenu, model);
                    }

                } else if (userSelection == 3) {

                } else if (userSelection == 4) {
                    while (true) {
                        int userSelectionFromEmployeeMenu = EmployeeView.employeeMenu();
                        if (userSelectionFromEmployeeMenu == 5) break;
                        EmployeeController.execute(userSelectionFromEmployeeMenu, model);
                    }
                } else if (userSelection == 5) {
                    while (true) {
                        int userSelectionFromReportMenu = OrderView.reportMenu();
                        if (userSelectionFromReportMenu == 6) break;
                        OrderController.createAReport(userSelectionFromReportMenu, model);
                    }
                } else if (userSelection == 6) {
                    if (MainMenuView.getConfirmationFromUser())
                        mapper.writeValue(new File("boatAppData.json"), model);
                } else if (userSelection == 7) {
                    if (MainMenuView.getConfirmationFromUser())
                        mapper.writeValue(new File("boatAppData.json"), model);
                    break MAINMENU;
                } else if (userSelection == 8) {
                    if (MainMenuView.getConfirmationFromUser())
                        mapper.writeValue(new File("boatAppData.json"), model);
                    System.out.println("Fijne Dagg...");
                    break MAIN;
                } else {
                    System.out.println("Invalid selection please choose an option that just in the list");
                }
            }
        }
    }
}
