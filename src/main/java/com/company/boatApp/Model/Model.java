package com.company.boatApp.Model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public List<Boat> boatList = new ArrayList<>();
    public List<Employee> employeeList = new ArrayList<>();
    public List<Client> clientList = new ArrayList<>();
    public List<Order> orderList = new ArrayList<>();


    @Override
    public String toString() {
        return "Model{" +
                "boatList=" + boatList +
                ", employeeList=" + employeeList +
                ", clientList=" + clientList +
                ", orderList=" + orderList +
                '}';
    }
}
