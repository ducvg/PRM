package com.example.project.Service;
import com.example.project.Service.ApiEndpoints.TaskApiEndpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static final String BASE_URL = "http://10.0.2.2:8080/api/";
    private TaskApiEndpoint taskApiEndpoint;

    private static ApiService apiServices;

    public static ApiService getInstance() {
        if (apiServices == null) {
            apiServices = new ApiService();
        }
        return apiServices;
    }

    private ApiService() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        taskApiEndpoint = retrofit.create(TaskApiEndpoint.class);
    }

    public static TaskApiEndpoint getTaskApiEndpoint() {
        return getInstance().taskApiEndpoint;
    }
}
