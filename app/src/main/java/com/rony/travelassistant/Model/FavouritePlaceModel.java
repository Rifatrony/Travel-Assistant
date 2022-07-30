package com.rony.travelassistant.Model;

public class FavouritePlaceModel {

    String place_name, place_details, travel_time, how_to_travel, where_to_stay, estimate_cost, image_link;

    public FavouritePlaceModel() {
    }

    public FavouritePlaceModel(String place_name, String place_details, String travel_time, String how_to_travel, String where_to_stay, String estimate_cost, String image_link) {
        this.place_name = place_name;
        this.place_details = place_details;
        this.travel_time = travel_time;
        this.how_to_travel = how_to_travel;
        this.where_to_stay = where_to_stay;
        this.estimate_cost = estimate_cost;
        this.image_link = image_link;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_details() {
        return place_details;
    }

    public void setPlace_details(String place_details) {
        this.place_details = place_details;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getHow_to_travel() {
        return how_to_travel;
    }

    public void setHow_to_travel(String how_to_travel) {
        this.how_to_travel = how_to_travel;
    }

    public String getWhere_to_stay() {
        return where_to_stay;
    }

    public void setWhere_to_stay(String where_to_stay) {
        this.where_to_stay = where_to_stay;
    }

    public String getEstimate_cost() {
        return estimate_cost;
    }

    public void setEstimate_cost(String estimate_cost) {
        this.estimate_cost = estimate_cost;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
