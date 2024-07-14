package com.example.project.Service.ApiEndpoints;

import com.example.project.model.ServiceModel.ListResponse;
import com.example.project.model.ServiceModel.TaskDTO;
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

    @GET("task/{id}")
    Call<Task> getTaskById(@Path("id") int taskId);

    @GET("task")
    Call<ListResponse<Task>> getFilterTasks(@Query("$filter") String filter);

    @GET("task")
    Call<ListResponse<Task>> getUserTask();

    @POST("task")
    Call<Task> createTask(@Body Task task);

    @PUT("task/{id}")
    Call<Task> updateTask(@Path("id") int taskId, @Body Task task);

    @PATCH("task/{id}")
    Call<Task> patchTask(@Path("id") int taskId, @Body Task task);

    @DELETE("task/{id}")
    Call<Void> deleteTask(@Path("id") int taskId);

    @POST("task/sync")
    Call<List<Task>> updateServer(@Body List<TaskDTO> userTasks);
}
