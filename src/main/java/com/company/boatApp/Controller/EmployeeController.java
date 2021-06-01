package com.company.boatApp.Controller;

import com.company.boatApp.Model.Employee;
import com.company.boatApp.Model.Model;
import com.company.boatApp.View.EmployeeView;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmployeeController {
    public static void execute(int userSelectionFromEmployeeMenu, Model model) {
        if (userSelectionFromEmployeeMenu == 1) {
            getEmployees(model);
        } else if (userSelectionFromEmployeeMenu == 2) {
            createAEmployee(model);
        } else if (userSelectionFromEmployeeMenu == 3) {
            updateAEmployee(model);
        } else if (userSelectionFromEmployeeMenu == 4) {
            deleteEmployee(model);
        }

    }

    private static void deleteEmployee(Model model) {
        getEmployees(model);
        int userSelection = EmployeeView.takeEmployeePreferenceFromUser();
        Employee employee = getAEmployee(model, userSelection);
        if (EmployeeView.takeConfirmationFromUser(employee.getLastName()))
            model.employeeList.remove(employee);
    }

    private static void updateAEmployee(Model model) {
        getEmployees(model);
        int userSelection = EmployeeView.takeEmployeePreferenceFromUser();
        Employee employee = getAEmployee(model, userSelection);
        while (true) {
            int userSelectionFromUpdateMenu = EmployeeView.updateMenu(employee.getLastName());
            if (userSelectionFromUpdateMenu == 1) {
                Map<String, String> employeeInfo = EmployeeView.takeEmployeNameInformationFromUser();
                employee.setFirstName(employeeInfo.get("Firstname"));
                employee.setLastName(employeeInfo.get("Lastname"));
            } else if (userSelectionFromUpdateMenu == 2) {
                String newUsername = EmployeeView.takeEmployeeUsernameFromUser();
                employee.setUserName(newUsername);
            } else if (userSelectionFromUpdateMenu == 3) {
                String newPassword = EmployeeView.takeEmployeePasswordFromUser();
                employee.setPassword(newPassword);
            } else if (userSelectionFromUpdateMenu == 4) break;
        }
    }

    private static Employee getAEmployee(Model model, int emloyeeId) {
        Optional<Employee> selectedEmployee = model.employeeList
                .stream().filter(employee -> employee.getEmployeeId() == emloyeeId).findFirst();
        return selectedEmployee.get();
    }

    private static void createAEmployee(Model model) {
        Map<String, String> employeeInfo = EmployeeView.takeEmployeeInformationFromUser();
        model.employeeList.add(new Employee(employeeInfo.get("Firstname"),
                employeeInfo.get("Lastname"),
                employeeInfo.get("Telephone_Number"),
                employeeInfo.get("Email_Address"),
                employeeInfo.get("Username"),
                employeeInfo.get("Password")));
        model.employeeList.stream().forEach(System.out::println);
    }

    private static void getEmployees(Model model) {
        EmployeeView.showReservationsLabel();
        model.employeeList.stream().forEach(employee ->
                EmployeeView.showReservations(employee.getEmployeeId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getUserName(),
                        employee.getStartingDate()));
    }

    public static Employee login(Model model) {
        List<String> userLoginInfo = EmployeeView.takeLoginInfoFromUser();
        if(authentication(userLoginInfo.get(0), userLoginInfo.get(1), model.employeeList))
            return  model.employeeList.stream().filter(employee -> employee.getUserName().equals(userLoginInfo.get(0))
                && employee.getPassword().equals(userLoginInfo.get(1))).findFirst().get();
        return null;
    }

    private static boolean authentication(String username, String password, List<Employee> employeeList) {
        return employeeList.stream().anyMatch(employee -> employee.getUserName().equals(username)
                && employee.getPassword().equals(password));
    }
}
