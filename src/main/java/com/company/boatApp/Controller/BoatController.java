package com.company.boatApp.Controller;

import com.company.boatApp.Model.Boat;

import java.util.List;
import java.util.Optional;

public class BoatController {
    public static Optional<Boat> getBoat(List<Boat> boatList, int boatId){
        return boatList.stream().filter(boat -> boat.getBoatId()==boatId).findFirst();
    }
}
