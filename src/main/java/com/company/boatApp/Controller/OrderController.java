package com.company.boatApp.Controller;

import com.company.boatApp.Model.*;
import com.company.boatApp.View.ClientView;
import com.company.boatApp.View.OrderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class OrderController {
    public static void execute(int userSelectionFromOrderMenu, Model model) throws ParseException {
        if (userSelectionFromOrderMenu == 1) {
            getReservations(model);
        } else if (userSelectionFromOrderMenu == 2) {
            makeAReservation(model);
        } else if (userSelectionFromOrderMenu == 3) {
            updateAReservation(model);
        }

    }

    private static void updateAReservation(Model model) throws ParseException {
        getReservations(model);
        int orderId = OrderView.takePreferencesFromUserToUpdateAReservation();
        Order order = getAReservation(model, orderId);
        int userSelection = OrderView.orderUpdateMenu(orderId);
        if (userSelection == 1) {
            String resDate = OrderView.takePreferencesFromUserAboutTourDate();
            updateOrder(order, resDate);
        } else if (userSelection == 2) {
            Map<String, String> userInfo = ClientView.takeClientInformationFromUser();
            Client client = ClientController.updateClient(order.getClient(), userInfo);
            System.out.println(order.getClient());
        } else if (userSelection == 3) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resDate = format.format(order.getRentingDate());
            List<Boat> availableBoatsAtASpecificDate = getAvailableBoatsAtASpecificDate(resDate, model);
            OrderView.showAvailableBoats(availableBoatsAtASpecificDate);
            int boatId = OrderView.takeBoatPreferenceFromUser();
            order.setBoat(BoatController.getBoat(model.boatList, boatId).get());
        } else if (userSelection == 4) deleteOrder(orderId, model);
    }

    private static void makeAReservation(Model model) throws ParseException {

        List<String> list = OrderView.takePreferencesFromUserAboutTour();
        //List<Boat> availableBoatsAtASpecificDate = getAvailableBoatsAtASpecificDate(list.get(0), model);
        String desiredTourDate = list.get(0);
        int desiredTourDuration = Integer.parseInt(list.get(1));
        List<Boat> availableBoatsAtASpecificDate = getAvailableBoats(desiredTourDate, desiredTourDuration, model);
        OrderView.showAvailableBoats(availableBoatsAtASpecificDate);
        int userBoatPreference = OrderView.takeBoatPreferenceFromUser();
        Boat boat = BoatController.getBoat(model.boatList, userBoatPreference).get();
        Map<String, String> userInfo = ClientView.takeClientInformationFromUser();
        Client client = ClientController.createClient(
                userInfo.get("Firstname"),
                userInfo.get("Lastname"),
                Integer.parseInt(userInfo.get("TelephoneNumber")),
                userInfo.get("EmailAddress"),
                userInfo.get("Address"));

        boolean isConfirm = OrderView.takeReservationConfirmationFromUser(
                list.get(0), boat.getType(), client.getFirstName(), client.getLastName(),
                boat.getMinimumPricePerHour() * desiredTourDuration);
        if (isConfirm) {
            reservationConfirmation(model, boat, client, model.employeeList.get(0), list.get(0), Integer.parseInt(list.get(1)));
        }

    }

    public static void getReservations(Model model) {
        OrderView.showReservationsLabel();
        model.orderList.stream().forEach(order ->
                OrderView.showReservations(order.getOrderId(),
                        order.getBoat().getBoatId(),
                        order.getBoat().getType(),
                        order.getRentingDate(),
                        order.getClient().getFirstName()));
    }

    public static Order getAReservation(Model model, int orderId) {
        Optional<Order> order = model.orderList.stream().filter(o -> o.getOrderId() == orderId).findFirst();
        return order.get();
    }

    /**
     * Deprecated method
     *
     * @param date
     * @param model
     * @return
     */
    private static List<Boat> getAvailableBoatsAtASpecificDate(String date, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Boat> tempBoatList = model.boatList;
        try {
            Date date1 = formatter.parse(date);
            List<Boat> boatList = model.orderList
                    .stream().
                            filter(order -> order.getRentingDate().equals(date1))
                    .map(order -> order.getBoat())
                    .collect(Collectors.toList());

            for (Boat boat : boatList) {
                tempBoatList = tempBoatList.stream().filter(boat1 -> boat.getBoatId() != boat1.getBoatId()).collect(Collectors.toList());
            }
            return tempBoatList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempBoatList;
    }

    private static List<Boat> getAvailableBoats(String date, int tourDuration, Model model) throws ParseException {
        Date desiredTourStartDate = setDate(date);
        Date desiredTourFinishDate = Date.from(desiredTourStartDate.toInstant().plusSeconds(tourDuration * 60 * 60));
        List<Boat> tempBoatList = model.boatList;

        List<Boat> boatList = model.orderList
                .stream().
                        filter(order -> {
                            Date tempDate = Date.from(
                                    order.getRentingDate()
                                            .toInstant()
                                            .plusSeconds(order.getRentingDuration() * 60 * 60)
                            );
                            return desiredTourFinishDate.after(order.getRentingDate()) &&
                                    desiredTourStartDate.before(tempDate);
                        })
                .map(order -> order.getBoat())
                .collect(Collectors.toList());
        for (Boat boat : boatList) {
            tempBoatList = tempBoatList.stream().filter(boat1 -> boat.getBoatId() != boat1.getBoatId()).collect(Collectors.toList());
        }
        return tempBoatList;
    }

    private static void reservationConfirmation(Model model, Boat boat, Client client, Employee employee,
                                                String tourDate, int tourDuration) throws ParseException {

        model.orderList.add(
                new Order(boat, client, employee, setDate(tourDate), tourDuration)
        );
        System.out.println("Reservation is done!");


    }

    private static void updateOrder(Order order, String resDate) throws ParseException {
        order.setRentingDate(setDate(resDate));
    }

    private static void deleteOrder(int orderId, Model model) {
        model.orderList.removeIf(order -> order.getOrderId() == orderId);
    }

    public static void createAReport(int userSelection, Model model) throws ParseException {
        if (userSelection == 1) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("daily");
            List<Order> orderList = getReport(model, "daily", reportDate);
            OrderView.showReport(orderList);
        } else if (userSelection == 2) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("weekly");
            List<Order> orderList = getReport(model, "weekly", reportDate);
            OrderView.showReport(orderList);
        } else if (userSelection == 3) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("monthly");
            List<Order> orderList = getReport(model, "monthly", reportDate);
            OrderView.showReport(orderList);
        } else if (userSelection == 4) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("yearly");
            List<Order> orderList = getReport(model, "yearly", reportDate);
            OrderView.showReport(orderList);
        }
    }

    private static List<Order> getReport(Model model, String reportLong, String startDate) throws ParseException {
        int lengthReport = 0;
        switch (reportLong) {
            case "daily":
                lengthReport = 1;
                break;
            case "weekly":
                lengthReport = 7;
                break;
            case "monthly":
                lengthReport = 30;
                break;
            case "yearly":
                lengthReport = 365;
                break;
        }
        LocalDate startingDate = LocalDate.parse(startDate);
        LocalDate endTime = startingDate.plusDays(lengthReport);
        List<Order> orderList = model.orderList.stream()
                .filter(order -> order.getRentingDate().after(Date.from(startingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        && order.getRentingDate().before(Date.from(endTime.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .collect(Collectors.toList());
        return orderList;
    }

    private static Date setDate(String reservationDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(reservationDate);
        return date;
    }
}