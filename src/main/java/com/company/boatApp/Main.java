package com.company.boatApp;

import com.company.boatApp.Controller.MainController;
import com.company.boatApp.Model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        Model model = mapper.readValue(new File("boatAppData.json"), Model.class);
        MainController.start(model, mapper);
    }
}
