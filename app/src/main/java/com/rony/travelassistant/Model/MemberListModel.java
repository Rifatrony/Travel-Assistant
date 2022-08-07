package com.rony.travelassistant.Model;

public class MemberListModel {

    String id, tour_id, tour_name, uid;

    public MemberListModel() {
    }

    public MemberListModel(String id, String tour_id, String tour_name, String uid) {
        this.id = id;
        this.tour_id = tour_id;
        this.tour_name = tour_name;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
