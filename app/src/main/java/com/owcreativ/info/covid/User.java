package com.owcreativ.info.covid;


//this is very simple class and it only contains the user attributes, a constructor and the getters
// you can easily do this by right click -> generate -> constructor and getters
public class User {

    private int id;
    private String username, phone, town,location;

    public User(int id, String username, String phone, String town, String location) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.town = town;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

     public String getPhone() {
        return phone;
    }

    public String getTown() {
        return town;
    }

    public String getLocation() {
        return location;
    }

}