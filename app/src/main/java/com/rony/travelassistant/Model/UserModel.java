package com.rony.travelassistant.Model;

public class UserModel {

    String name, email, number, password, confirm_password, uid;

    public UserModel() {
    }

    public UserModel(String name, String email, String number, String password, String confirm_password, String uid) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.password = password;
        this.confirm_password = confirm_password;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
