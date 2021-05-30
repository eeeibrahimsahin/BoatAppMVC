package com.company.boatApp.Controller;

import com.company.boatApp.Model.Client;
import com.company.boatApp.Model.Model;
import com.company.boatApp.View.ClientView;

import java.util.Map;

public class ClientController {


    public static Client createClient(String firstName, String lastName, int telephoneNumber, String address,
                                      String emailAddress) {
        Client client = new Client();
        return client.createClient(firstName,lastName,telephoneNumber,address,emailAddress);
    }

    public static Client updateClient(Client client,Map<String,String> userInfo){
        client.setFirstName(userInfo.get("Firstname"));
        client.setLastName(userInfo.get("Lastname"));
        client.setTelephoneNumber(Integer.parseInt(userInfo.get("TelephoneNumber")));
        client.setAddress(userInfo.get("EmailAddress"));
        client.setEmailAddress(userInfo.get("Address"));
        return client;
    }

    public static void execute(int userSelection, Model model) {
        if (userSelection == 1) {
            ClientView.showClients(model);
        } else if (userSelection == 2) {
            ClientView.showClients(model);
            int userSelecttion = ClientView.takeUserPreferenceForChoosingClient(model);
            Client client = getClient(model, userSelecttion);
            Map<String, String> clientInfo = ClientView.takeClientInformationFromUser();
            updateClient(client,clientInfo);
            System.out.println(model.clientList.get(0));
        }
    }

public static Client getClient(Model model,int clientId){
    return model.clientList.stream().filter(client -> client.getClientId()==clientId).findFirst().get();
}
}
