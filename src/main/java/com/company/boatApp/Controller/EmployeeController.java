package com.company.boatApp.Controller;

import com.company.boatApp.Model.Employee;
import com.company.boatApp.Model.Model;
import com.company.boatApp.View.EmployeeView;

import java.util.List;

public class EmployeeController {
    public static boolean login(Model model) {
        List<String> userLoginInfo = EmployeeView.takeLoginInfoFromUser();
        return authentication(userLoginInfo.get(0), userLoginInfo.get(1), model.employeeList);
    }

    private static boolean authentication(String username, String password, List<Employee> employeeList) {
        return employeeList.stream().anyMatch(employee -> employee.getUserName().equals(username)
                && employee.getPassword().equals(password));
    }
}
