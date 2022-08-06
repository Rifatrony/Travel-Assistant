package com.dpheapp.temporaryproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {


    @GET("product/featured")
    Call<ApiResponseModel> getData();

}
