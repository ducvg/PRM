package com.example.project.Service.ApiEndpoints;

import com.example.project.model.ListResponse;
import com.example.project.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiEndpoint {
    @GET("User")
    Call<ListResponse<User>> getAllTask();

    @GET("User/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("User")
    Call<User> register(@Body User user);

    @PUT("User/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @PATCH("User/{id}")
    Call<User> patchUser(@Path("id") int id, @Body User user);

    @DELETE("User/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
