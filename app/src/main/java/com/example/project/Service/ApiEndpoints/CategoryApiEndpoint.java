package com.example.project.Service.ApiEndpoints;

import com.example.project.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApiEndpoint {
        @GET("/Category")
        Call<List<Category>> getAllTask();

        @GET("/Category/{id}")
        Call<Category> getCategoryById(@Path("id") int id);

        @GET("/Category?$filter=name eq '{name}'")
        Call<List<Category>> getCategoryByName(@Path("name") String name);

        @POST("/Category")
        Call<Category> createCategory(@Body Category user);

        @PUT("/Category/{id}")
        Call<Category> updateCategory(@Path("id") int id, @Body Category cate);

        @PATCH("/Category/{id}")
        Call<Category> patchCategory(@Path("id") int id, @Body Category cate);

        @DELETE("/Category/{id}")
        Call<Void> deleteCategory(@Path("id") int id);
}
