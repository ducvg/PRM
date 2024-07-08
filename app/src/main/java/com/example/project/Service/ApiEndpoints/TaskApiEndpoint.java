package com.example.project.Service.ApiEndpoints;

import com.example.project.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskApiEndpoint {
    @GET("/task")
    Call<List<Task>> getAllTask();

    @GET("/comments/{id}")
    Call<Task> getTaskById(@Path("id") int taskId);

    @GET("/task?$filter=userId eq {userId}")
    Call<List<Task>> getTaskByUserId(@Query("userId") int userId);

    @POST("/task")
    Call<Task> createTask(@Body Task task);

    @PUT("/task/{id}")
    Call<Task> updateTask(@Path("id") int taskId, @Body Task task);

    @PATCH("/task/{id}")
    Call<Task> patchTask(@Path("id") int taskId, @Body Task task);

    @DELETE("/task/{id}")
    Call<Void> deleteTask(@Path("id") int taskId);
}
