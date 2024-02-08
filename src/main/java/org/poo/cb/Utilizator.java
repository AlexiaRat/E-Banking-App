package org.poo.cb;
import java.util.*;
import java.io.*;
public class Utilizator {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private Portofoliu portfolio;
    private List<Utilizator> friends;
    private List<String> notifications;

    public Utilizator(String email, String firstName, String lastName, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.portfolio = new Portofoliu();
        this.friends = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }


    public void addFriend(Utilizator friend) {
        friends.add(friend);
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public Portofoliu getPortfolio() {
        return portfolio;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }
    public void addNotification(String message) {
        notifications.add(message);
    }


}

class UtilizatorBuilder {
    private String email;
    private String firstName;
    private String lastName;
    private String address;

    public UtilizatorBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UtilizatorBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UtilizatorBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UtilizatorBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Utilizator build() {
        return new Utilizator(email, firstName, lastName, address);
    }
}
