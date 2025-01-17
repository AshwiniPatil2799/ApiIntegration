package com.example.testapptransactions.Client;

import com.example.testapptransactions.Data.TransactionEntity;
import com.example.testapptransactions.Modal.LoginRequest;
import com.example.testapptransactions.Modal.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("endpoint") //use your api end point
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


    @GET("endpoint")
    Call<List<TransactionEntity>> getTransactions(@Header("Authorization") String token);
}
