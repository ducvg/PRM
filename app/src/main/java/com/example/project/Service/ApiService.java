package com.example.project.Service;
import com.example.project.Service.ApiEndpoints.CategoryApiEndpoint;
import com.example.project.Service.ApiEndpoints.SubtaskApiEndpoint;
import com.example.project.Service.ApiEndpoints.TaskApiEndpoint;
import com.example.project.Service.ApiEndpoints.UserApiEndpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static final String BASE_URL = "http://10.0.2.2:5001/api/";
    private TaskApiEndpoint taskApiEndpoint;
    private UserApiEndpoint userApiEndpoint;
    private CategoryApiEndpoint categoryApiEndpoint;
    private SubtaskApiEndpoint subtaskApiEndpoint;

    private static ApiService apiServices;

    public static ApiService getInstance() {
        if (apiServices == null) {
            apiServices = new ApiService();
        }
        return apiServices;
    }

    private ApiService() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        taskApiEndpoint = retrofit.create(TaskApiEndpoint.class);
        userApiEndpoint = retrofit.create(UserApiEndpoint.class);
        categoryApiEndpoint = retrofit.create(CategoryApiEndpoint.class);
        subtaskApiEndpoint = retrofit.create(SubtaskApiEndpoint.class);
    }

    public static TaskApiEndpoint getTaskApiEndpoint() {
        return getInstance().taskApiEndpoint;
    }
    public static UserApiEndpoint getUserApiEndpoint() {
        return getInstance().userApiEndpoint;
    }
    public static CategoryApiEndpoint getCategoryApiEndpoint() {
        return getInstance().categoryApiEndpoint;
    }
    public static SubtaskApiEndpoint getSubtaskApiEndpoint() {
        return getInstance().subtaskApiEndpoint;
    }
}
