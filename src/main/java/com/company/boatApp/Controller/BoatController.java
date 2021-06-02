package com.company.boatApp.Controller;

import com.company.boatApp.Model.*;
import com.company.boatApp.View.BoatView;
import com.company.boatApp.View.EmployeeView;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoatController {
    public static void execute(int userSelectionFromBoatMenu, Model model) {
        if (userSelectionFromBoatMenu == 1) {
            BoatView.showNotAvailableBoats(getBoats(model));
        } else if (userSelectionFromBoatMenu == 2) {
            BoatView.showNotAvailableBoats(getNotAvailableBoatsAtTheMoment(model));
            createBoat(model);
        } else if (userSelectionFromBoatMenu == 3) {
            updateBoat(model);
        } else if (userSelectionFromBoatMenu == 4) {
            deleteBoat(model);
        }
    }

    private static List<Boat> getBoats(Model model) {
        List<Boat> notAvailableBoatsAtTheMoment = getNotAvailableBoatsAtTheMoment(model);
        getAvailableBoatsAtTheMoment(model);
        List<Boat> tempList = model.boatList;
        for (Boat boat : notAvailableBoatsAtTheMoment) {
            tempList = model.boatList.stream().map(boat1 -> {
                if (boat1.getBoatId() == boat.getBoatId()) {
                    return boat;
                } else {
                    return boat1;
                }
            }).collect(Collectors.toList());
        }
        return tempList;
    }

    private static void deleteBoat(Model model) {
    }

    private static void updateBoat(Model model) {
    }

    private static void createBoat(Model model) {
    }

    private static List<Boat> getNotAvailableBoatsAtTheMoment(Model model) {
        Date now = new Date(System.currentTimeMillis());
        List<Boat> notAvailableBoats = model.orderList.stream().filter(order -> {
            Date tourEndDate = Date.from(
                    order.getRentingDate()
                            .toInstant()
                            .plusSeconds(order.getRentingDuration() * 60 * 60)
                            .plusSeconds(15 * 60)

            );
            if (order.getBoat().getType() == BoatType.ELECTRICALBOAT) {
                tourEndDate = Date.from(tourEndDate.toInstant().plusSeconds((order.getBoat().getChargingTime() - 15) * 60));
            }
            return order.getRentingDate().before(now) && now.before(tourEndDate);
        }).map(order -> {
            order.getBoat().setStatus(BoatStatus.UNAVAILABLE);
            if (order.getBoat().getType() == BoatType.ELECTRICALBOAT) {
                double remainCharge =
                        100 - (100 * ((now.getTime() - order.getRentingDate().getTime()) / 60_000) / order.getBoat().getChargeLife());
                order.getBoat().setRemainingCharge(remainCharge);
            }
            return order.getBoat();
        }).collect(Collectors.toList());

        return notAvailableBoats;
    }

    private static List<Boat> getAvailableBoatsAtTheMoment(Model model) {
        List<Boat> notAvailableBoatsAtTheMoment = getNotAvailableBoatsAtTheMoment(model);
        List<Boat> availableBoatList = model.boatList.stream()
                .filter(boat ->
                        !notAvailableBoatsAtTheMoment.stream()
                                .anyMatch(boat1 -> boat1.getBoatId() == boat.getBoatId()))
                .map(boat -> {
                    boat.setStatus(BoatStatus.AVAILABLE);
                    if (boat.getType() == BoatType.ELECTRICALBOAT) {
                        boat.setRemainingCharge(100);
                    }
                    return boat;
                }).collect(Collectors.toList());
        return availableBoatList;
    }

    public static Optional<Boat> getBoat(List<Boat> boatList, int boatId) {
        return boatList.stream().filter(boat -> boat.getBoatId() == boatId).findFirst();
    }

    private static double getRemainChargeOfBoat() {
        return 0.0;
    }
}
