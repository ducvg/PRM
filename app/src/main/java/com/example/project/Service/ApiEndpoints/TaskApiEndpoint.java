package com.example.project.Service.ApiEndpoints;

import com.example.project.model.Category;
import com.example.project.model.ListResponse;
import com.example.project.model.Subtask;
import com.example.project.model.Task;
import com.example.project.model.User;

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
    Call<ListResponse<Task>> getTask();

    @GET("task")
    Call<ListResponse<Task>> getFilterTasks(@Query("$filter") String filter);

    @POST("task")
    Call<Task> createTask(@Body Task task);

    @PUT("task/{id}")
    Call<Task> updateTask(@Path("id") int taskId, @Body Task task);

    @PATCH("task/{id}")
    Call<Task> patchTask(@Path("id") int taskId, @Body Task task);

    @DELETE("task/{id}")
    Call<Void> deleteTask(@Path("id") int taskId);

    @GET("User")
    Call<ListResponse<User>> getUsers();

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
    @GET("/task")
    Call<ListResponse<Subtask>> getSubtask();

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
    @GET("/Category")
    Call<ListResponse<Category>> getCategories();

    @GET("/Category/{id}")
    Call<Category> getCategoryById(@Path("id") int id);

    @GET("/Category?$filter=name eq '{name}'")
    Call<ListResponse<Category>> getCategoryByName(@Path("name") String name);

    @POST("/Category")
    Call<Category> createCategory(@Body Category user);

    @PUT("/Category/{id}")
    Call<Category> updateCategory(@Path("id") int id, @Body Category cate);

    @PATCH("/Category/{id}")
    Call<Category> patchCategory(@Path("id") int id, @Body Category cate);

    @DELETE("/Category/{id}")
    Call<Void> deleteCategory(@Path("id") int id);
}
