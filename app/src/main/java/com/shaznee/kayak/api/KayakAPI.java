package com.shaznee.kayak.api;

import com.shaznee.kayak.models.Airline;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface KayakAPI {

    @GET("/h/mobileapis/directory/airlines")
    void getAirLines(Callback<List<Airline>> response);

}
