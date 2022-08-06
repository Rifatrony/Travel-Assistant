package com.rony.travelassistant.Model;

public class BalanceModel {

    String amount, balance_id, added_by_id, tour_id;

    public BalanceModel() {
    }

    public BalanceModel(String amount, String balance_id, String added_by_id, String tour_id) {
        this.amount = amount;
        this.balance_id = balance_id;
        this.added_by_id = added_by_id;
        this.tour_id = tour_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance_id() {
        return balance_id;
    }

    public void setBalance_id(String balance_id) {
        this.balance_id = balance_id;
    }

    public String getAdded_by_id() {
        return added_by_id;
    }

    public void setAdded_by_id(String added_by_id) {
        this.added_by_id = added_by_id;
    }

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }
}
