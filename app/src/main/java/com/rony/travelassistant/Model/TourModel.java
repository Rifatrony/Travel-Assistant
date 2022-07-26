package com.rony.travelassistant.Model;

public class TourModel {

    String tour_name, start_date, end_date, id, uid;

    public TourModel() {
    }

    public TourModel(String tour_name, String start_date, String end_date, String id, String uid) {
        this.tour_name = tour_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.id = id;
        this.uid = uid;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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
}
