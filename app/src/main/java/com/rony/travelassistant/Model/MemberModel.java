package com.rony.travelassistant.Model;

public class MemberModel {

    String added_by_uid, amount, date, email, id, name, number, tour_id, tour_name, per_person_cost;

    public MemberModel() {
    }

    public MemberModel(String added_by_uid, String amount, String date, String email, String id, String name, String number, String tour_id, String tour_name, String per_person_cost) {
        this.added_by_uid = added_by_uid;
        this.amount = amount;
        this.date = date;
        this.email = email;
        this.id = id;
        this.name = name;
        this.number = number;
        this.tour_id = tour_id;
        this.tour_name = tour_name;
        this.per_person_cost = per_person_cost;
    }

    public String getAdded_by_uid() {
        return added_by_uid;
    }

    public void setAdded_by_uid(String added_by_uid) {
        this.added_by_uid = added_by_uid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getPer_person_cost() {
        return per_person_cost;
    }

    public void setPer_person_cost(String per_person_cost) {
        this.per_person_cost = per_person_cost;
    }
}
