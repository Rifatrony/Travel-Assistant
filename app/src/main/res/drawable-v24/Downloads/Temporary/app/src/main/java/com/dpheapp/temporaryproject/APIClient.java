package com.dpheapp.temporaryproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static Retrofit retrofit = null;

    public static APIInterface getClient(){

        if (retrofit == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            Gson gson = new GsonBuilder().create();

            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://faruqtraders.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit.create(APIInterface.class);
    }
}
