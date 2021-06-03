package com.company.boatApp.Controller;

import com.company.boatApp.Model.*;
import com.company.boatApp.View.BoatView;

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
        BoatView.showNotAvailableBoats(getBoats(model));
        int boatId = BoatView.takeBoatSelectionPrefernceFromUser();
        Optional<Boat> boat = model.boatList.stream().filter(boat1 -> boat1.getBoatId() == boatId).findFirst();
        int userSelection = BoatView.boatUpdateMenu();
        if (userSelection == 1) {
            int seatsNumber = BoatView.takeSeatsNumberPreferenceFromUser();
            boat.get().setSeats(seatsNumber);
        } else if (userSelection == 2) {
            double minPrice = BoatView.takeMinimumPriceFromUser();
            boat.get().setMinimumPricePerHour(minPrice);
        } else if (userSelection == 3) {
            int chargingTime = BoatView.takeChargingTimeFromUser();
            boat.get().setChargingTime(chargingTime);
        } else if (userSelection == 4) {
            boolean isConfirmed = BoatView.takeConfirmationFromUserToDeleteBaot(boat.get());
            if (isConfirmed) model.boatList.removeIf(boat1 -> boat1.getBoatId() == boatId);
        }
    }

    private static void createBoat(Model model) {
        int userSelection = BoatView.addBoatMenu();
        if (userSelection == 5) return;
        List<String> boatInfo = BoatView.takeUserSelectionToAddBoat(userSelection);
        int boatId = 0;
        BoatType boatType = null;
        if (userSelection == 1) {
            long count = model.boatList.stream().filter(boat -> boat.getType().equals(BoatType.KAJAK)).count();
            boatId = 100 + (int) ++count;
            boatType = BoatType.KAJAK;
        } else if (userSelection == 2) {
            boatType = BoatType.SUPBOARD;
            long count = model.boatList.stream().filter(boat -> boat.getType().equals(BoatType.SUPBOARD)).count();
            boatId = 300 + (int) ++count;
        } else if (userSelection == 3) {
            boatType = BoatType.ROWINGBOAT;
            long count = model.boatList.stream().filter(boat -> boat.getType().equals(BoatType.ROWINGBOAT)).count();
            boatId = 200 + (int) ++count;
        } else if (userSelection == 4) {
            boatType = BoatType.ELECTRICALBOAT;
            long count = model.boatList.stream().filter(boat -> boat.getType().equals(BoatType.ELECTRICALBOAT)).count();
            boatId = 400 + (int) ++count;
        }
        boolean isConfirmed = BoatView.takeConfirmationFromUserToCreateBoat(boatType, boatInfo);
        if (isConfirmed) {
            if (boatType.equals(BoatType.ELECTRICALBOAT)) {
                model.boatList.add(new Boat(boatId, boatType,
                        Integer.parseInt(boatInfo.get(0)), Integer.parseInt(boatInfo.get(2)),
                        Double.parseDouble(boatInfo.get(1))));
            } else {
                model.boatList.add(new Boat(boatId, boatType, Integer.parseInt(boatInfo.get(0)),
                        Double.parseDouble(boatInfo.get(1))));
            }
        }
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
                tourEndDate = Date.from(tourEndDate.toInstant().plusSeconds((order.getBoat().getChargingTime()) * 60));
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
