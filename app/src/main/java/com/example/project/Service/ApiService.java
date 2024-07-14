package com.example.project.Service;
import androidx.annotation.NonNull;

import com.example.project.main.AppConfig;
import com.example.project.Service.ApiEndpoints.CategoryApiEndpoint;
import com.example.project.Service.ApiEndpoints.SubtaskApiEndpoint;
import com.example.project.Service.ApiEndpoints.TaskApiEndpoint;
import com.example.project.Service.ApiEndpoints.UserApiEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
//    public static final String BASE_URL = "http://192.168.1.128:5100/api/";
    public static final String BASE_URL = "http://192.168.1.14:5100/api/";


    private TaskApiEndpoint taskApiEndpoint;
    private UserApiEndpoint userApiEndpoint;
    private SubtaskApiEndpoint subtaskApiEndpoint;
    private CategoryApiEndpoint categoryApiEndpoint;


    private static ApiService apiServices;

    public static ApiService getInstance() {
        if (apiServices == null) {
            apiServices = new ApiService();
        }
        return apiServices;
    }

    private ApiService() {

        Interceptor interceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                builder.addHeader("Authorization", "Bearer "+ AppConfig.token);
                return chain.proceed(builder.build());
            }
        };

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder().addInterceptor(interceptor);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okBuilder.build())
                .build();

        taskApiEndpoint = retrofit.create(TaskApiEndpoint.class);
        userApiEndpoint = retrofit.create(UserApiEndpoint.class);
        subtaskApiEndpoint = retrofit.create(SubtaskApiEndpoint.class);
        categoryApiEndpoint = retrofit.create(CategoryApiEndpoint.class);
    }

    public static TaskApiEndpoint getTaskApiEndpoint() {
        return getInstance().taskApiEndpoint;
    }
    public static UserApiEndpoint getUserApiEndpoint() {
        return getInstance().userApiEndpoint;
    }
    public static SubtaskApiEndpoint getSubtaskApiEndpoint() {
        return getInstance().subtaskApiEndpoint;
    }
    public static CategoryApiEndpoint getCategoryApiEndpoint() {
        return getInstance().categoryApiEndpoint;
    }
}
