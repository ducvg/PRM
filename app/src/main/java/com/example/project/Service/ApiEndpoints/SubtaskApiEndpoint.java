package com.example.project.Service.ApiEndpoints;

import com.example.project.model.ServiceModel.ListResponse;
import com.example.project.model.Subtask;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SubtaskApiEndpoint {
    @GET("/task")
    Call<ListResponse<Subtask>> getAllTask();

    @GET("/comments/{id}")
    Call<Subtask> getSubtaskById(@Path("id") int taskId);

    @GET("/task?$filter=taskid eq {id}")
    Call<ListResponse<Subtask>> getSubtaskByUserId(@Path("id") int id);

    @POST("/task")
    Call<Subtask> createSubtask(@Body Subtask task);

    @PUT("/task/{id}")
    Call<Subtask> updateSubtask(@Path("id") int taskId, @Body Subtask task);

    @PATCH("/task/{id}")
    Call<Subtask> patchSubtask(@Path("id") int taskId, @Body Subtask task);

    @DELETE("/task/{id}")
    Call<Void> deleteSubtask(@Path("id") int taskId);
}
