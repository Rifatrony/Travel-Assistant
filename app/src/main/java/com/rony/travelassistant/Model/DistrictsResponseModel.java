package com.rony.travelassistant.Model;

import java.util.ArrayList;

public class DistrictsResponseModel {

    public Status status;
    public ArrayList<Datum> data;

    public DistrictsResponseModel() {
    }

    public DistrictsResponseModel(Status status, ArrayList<Datum> data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Status{
        public int code;
        public String message;
        public String date;
    }

    public class Datum{
        public String _id;
        public String district;
        public String districtbn;
        public String coordinates;

        public Datum(String _id, String district, String districtbn, String coordinates) {
            this._id = _id;
            this.district = district;
            this.districtbn = districtbn;
            this.coordinates = coordinates;
        }

    }

}
