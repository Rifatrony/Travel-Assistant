package com.rony.travelassistant.Model;

public class TourIDModel {

    String t_id, tour_id;

    public TourIDModel() {
    }

    public TourIDModel(String t_id, String tour_id) {
        this.t_id = t_id;
        this.tour_id = tour_id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }
}
