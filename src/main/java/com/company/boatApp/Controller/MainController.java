package com.company.boatApp.Controller;

import com.company.boatApp.Model.Client;
import com.company.boatApp.Model.Model;
import com.company.boatApp.View.ClientView;
import com.company.boatApp.View.MainMenuView;
import com.company.boatApp.View.OrderView;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.text.ParseException;

public class MainController {
    public void start(Model model, ObjectMapper mapper) throws ParseException, IOException {
        MAIN:
        while (true) {
            while (true) {
                if (EmployeeController.login(model)) break;
            }
          MAINMENU:  while (true) {
                int userSelection = MainMenuView.mainMenu();
                if (userSelection == 1) {
                    while (true) {
                        int userSelectionFromOrderMenu = OrderView.orderMenu();
                        if (userSelectionFromOrderMenu == 5) break;
                        OrderController.execute(userSelectionFromOrderMenu, model);
                    }
                } else if (userSelection == 2) {
                    while (true) {
                        int userSelectionFromClientMenu = ClientView.clientMenu();
                        if (userSelectionFromClientMenu == 4) break;
                        ClientController.execute(userSelectionFromClientMenu, model);
                    }

                } else if (userSelection == 3) {

                } else if (userSelection == 4) {

                } else if (userSelection == 5) {
                    while (true) {
                        int userSelectionFromReportMenu = OrderView.reportMenu();
                        if (userSelectionFromReportMenu == 5) break;
                        OrderController.createAReport(userSelectionFromReportMenu, model);
                    }
                } else if (userSelection == 6) {
                    if(MainMenuView.getConfirmationFromUser())
                        mapper.writeValue(new File("boatAppData.json"), model);
                } else if (userSelection == 7) {
                    break MAINMENU;
                } else if (userSelection == 8) {
                    if(MainMenuView.getConfirmationFromUser())
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
