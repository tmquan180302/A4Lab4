package com.example.a4lab4.Interface;

import com.example.a4lab4.Model.User;
import com.example.a4lab4.ServerRequest;
import com.example.a4lab4.ServerRespone;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @POST("addUser/")
    Call<ServerRespone> operation (@Body ServerRequest request);

//    Call<ServerRespone> login(@Body ServerRequest request);
    @POST("login/")
    Call<User> login (@Body String email, String password);
}
