package com.company.boatApp.Model;

import java.util.Random;

public class Client {
    /**
     * CreditCard: using account number.
     */
    private Random random = new Random();
    private int clientId;
    private String firstName;
    private String lastName;
    private int telephoneNumber;
    private String address;
    private String emailAddress;
    private String creditCard; // we can use object here instead of String type.

    public Client() {
    }

    public Client(String firstName, String lastName, int telephoneNumber, String address, String emailAddress) {
        this.clientId = random.nextInt(1000);
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
    }

    public Client createClient(String firstName, String lastName, int telephoneNumber, String address, String emailAddress) {
        return new Client(firstName, lastName, telephoneNumber, address, emailAddress);
    }

    public int getClientId() {
        return clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephoneNumber=" + telephoneNumber +
                ", address='" + address + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
