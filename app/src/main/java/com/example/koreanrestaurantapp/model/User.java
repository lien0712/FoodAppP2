package com.example.koreanrestaurantapp.model;

public class User {

    private String name;
    private String password;
    private String phone;
    private String active;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String active) {
        this.name = name;
        this.password = password;
        this.active = active;
    }

    private User(String name, String password, String phone, String active) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.active = active;
    }
    public User() {

    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
