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
import java.util.stream.Stream;

public class OrderController {
    public static void execute(int userSelectionFromOrderMenu, Employee employee, Model model) throws ParseException {
        statusController(model);
        if (userSelectionFromOrderMenu == 1) {
            getReservations(model);
        } else if (userSelectionFromOrderMenu == 2) {
            makeAReservation(employee, model);
        } else if (userSelectionFromOrderMenu == 3) {
            updateAReservation(model);
        } else if (userSelectionFromOrderMenu == 4) {
            deleteOrder(model);
        } else if (userSelectionFromOrderMenu == 5) {
            getReservations(model);
            int userSelection = OrderView.takePreferencesFromUserToUpdateAReservation();
            Order order = getAReservation(model, userSelection);
            double totalPrice = OrderView.getTotalPrice(order, 5);
            System.out.println("totalPrice = " + totalPrice);
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
            changeReservationStatus(order);
        } else if (userSelection == 3) {
            Map<String, String> userInfo = ClientView.takeClientInformationFromUser();
            Client client = ClientController.updateClient(order.getClient(), userInfo);
            System.out.println(order.getClient());
        } else if (userSelection == 4) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resDate = format.format(order.getRentingDate());
            List<Boat> availableBoatsAtASpecificDate = getAvailableBoatsAtASpecificDate(resDate, model);
            OrderView.showAvailableBoats(availableBoatsAtASpecificDate);
            int boatId = OrderView.takeBoatPreferenceFromUser();
            order.setBoat(BoatController.getBoat(model.boatList, boatId).get());
        }
    }

    private static void makeAReservation(Employee employee, Model model) throws ParseException {

        List<String> list = OrderView.takePreferencesFromUserAboutTour();
        //List<Boat> availableBoatsAtASpecificDate = getAvailableBoatsAtASpecificDate(list.get(0), model);
        String desiredTourDate = list.get(0);
        int desiredTourDuration = Integer.parseInt(list.get(1));
        List<Boat> availableBoatsAtASpecificDate = getAvailableBoats(desiredTourDate, desiredTourDuration, model);
        OrderView.showAvailableBoats(availableBoatsAtASpecificDate);
        int userBoatPreference = OrderView.takeBoatPreferenceFromUser();
        Boat boat = BoatController.getBoat(model.boatList, userBoatPreference).get();
        Map<String, String> userInfo = ClientView.takeClientInformationFromUser();
        Client client = ClientController.createClient(model,
                userInfo.get("Firstname"),
                userInfo.get("Lastname"),
                Integer.parseInt(userInfo.get("TelephoneNumber")),
                userInfo.get("Address"),
                userInfo.get("EmailAddress"));

        boolean isConfirm = OrderView.takeReservationConfirmationFromUser(
                list.get(0), boat.getType(), client.getFirstName(), client.getLastName(),
                boat.getMinimumPricePerHour() * desiredTourDuration);
        if (isConfirm) {
            reservationConfirmation(model, boat, client, employee, list.get(0), Integer.parseInt(list.get(1)));
        }

    }

    public static void getReservations(Model model) {
        OrderView.showReservationsLabel();
        model.orderList.sort(Comparator.comparing(Order::getRentingDate));
        model.orderList.stream().forEach(order ->
                OrderView.showReservations(order.getOrderId(),
                        order.getBoat().getBoatId(),
                        order.getBoat().getType(),
                        order.getRentingDate(),
                        order.getClient().getFirstName(),
                        order.getTourStatus(),
                        order.getEmployee().getFirstName()));
    }

    public static Order getAReservation(Model model, int orderId) {
        Optional<Order> order = model.orderList.stream().filter(o -> o.getOrderId() == orderId).findFirst();
        return order.get();
    }

    /**
     * Deprecated method
     * This method is deprecated. Please use the new version of it which is getAvailableBoat.
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

    private static void deleteOrder(Model model) {
        getReservations(model);
        int orderId = OrderView.takePreferencesFromUserToUpdateAReservation();
        model.orderList.removeIf(order -> order.getOrderId() == orderId);
    }

    public static void createAReport(int userSelection, Model model) throws ParseException {
        if (userSelection == 1) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("daily");
            Map<String, List<Order>> orderMap = getReport(model, "daily", reportDate);
            OrderView.showReport(orderMap);
        } else if (userSelection == 2) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("weekly");
            Map<String, List<Order>> orderMap = getReport(model, "weekly", reportDate);
            OrderView.showReport(orderMap);
        } else if (userSelection == 3) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("monthly");
            Map<String, List<Order>> orderMap = getReport(model, "monthly", reportDate);
            OrderView.showReport(orderMap);
        } else if (userSelection == 4) {
            String reportDate = OrderView.takeDatePreferenceFromUserToCreateReport("yearly");
            Map<String, List<Order>> orderMap = getReport(model, "yearly", reportDate);
            OrderView.showReport(orderMap);
        } else if (userSelection == 5) {
            Map<BoatType, Long> boatMap = model.boatList.stream()
                    .collect(Collectors.groupingBy(e -> e.getType(), Collectors.counting()));
            OrderView.showBoatReport(boatMap);
        }
    }

    public static void changeReservationStatus(Order order) {
        int userSelection = OrderView.takeStatusTypePreferenceFromUser();
        TourStatus tourStatus = null;
        if (userSelection == 1) {
            tourStatus = TourStatus.WAITING;
        } else if (userSelection == 2) {
            tourStatus = TourStatus.COMPLETED;
        } else if (userSelection == 3) {
            tourStatus = TourStatus.CANCELED;
        }
        order.setTourStatus(tourStatus);
    }

    private static Map<String, List<Order>> getReport(Model model, String reportLong, String startDate)
            throws ParseException {
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
        List<Order> allOrderList = model.orderList.stream()
                .filter(order ->
                        order.getRentingDate()
                                .after(Date.from(startingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                && order.getRentingDate()
                                .before(Date.from(endTime.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .collect(Collectors.toList());
        List<Order> completedOrderList = allOrderList.stream()
                .filter(order -> order.getTourStatus() == TourStatus.COMPLETED).collect(Collectors.toList());
        List<Order> estimatedOrderList = allOrderList.stream()
                .filter(order -> order.getTourStatus() == TourStatus.WAITING).collect(Collectors.toList());
        List<Order> canceledOrderList = allOrderList.stream()
                .filter(order -> order.getTourStatus() == TourStatus.CANCELED).collect(Collectors.toList());

        Map<String, List<Order>> orderMap = new HashMap<>();
        orderMap.put("All_Order_List", allOrderList);
        orderMap.put("Completed_Order_List", completedOrderList);
        orderMap.put("Estimated_Order_List", estimatedOrderList);
        orderMap.put("Canceled_Order_List", canceledOrderList);
        return orderMap;
    }

    private static void statusController(Model model) {
        Date now = new Date(System.currentTimeMillis());
        model.orderList
                .stream().
                filter(order -> {
                    Date tempDate = Date.from(
                            order.getRentingDate()
                                    .toInstant()
                                    .plusSeconds(order.getRentingDuration() * 60 * 60)
                    );
                    return now.after(tempDate) && order.getTourStatus() != TourStatus.CANCELED;
                })
                .map(order -> {
                            order.setTourStatus(TourStatus.COMPLETED);
                            return order;
                        }
                )
                .collect(Collectors.toList());
    }

    private static Date setDate(String reservationDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(reservationDate);
        return date;
    }
}
