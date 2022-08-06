package com.rony.travelassistant.Model;

public class CostModel {

    String amount, date, id, uid, reason, tour_id;

    public CostModel() {
    }

    public CostModel(String amount, String date, String id, String uid, String reason, String tour_id) {
        this.amount = amount;
        this.date = date;
        this.id = id;
        this.uid = uid;
        this.reason = reason;
        this.tour_id = tour_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }
}
