package com.rony.travelassistant.Api;

import com.rony.travelassistant.Model.DistrictsResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("districts")
    Call<DistrictsResponseModel> getDistrict();
}
