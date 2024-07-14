package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.Service.ApiService;
import com.example.project.dal.SQLiteHelper;
import com.example.project.main.AppConfig;
import com.example.project.model.Category;
import com.example.project.model.ServiceModel.ListResponse;
import com.example.project.model.ServiceModel.LoginDTO;
import com.example.project.model.ServiceModel.TaskDTO;
import com.example.project.model.ServiceModel.TokenDTO;
import com.example.project.model.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView txtOffline;
    private Button btnLogin;
    private Context context;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        context = this;
        db = new SQLiteHelper(this);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::login);
        txtOffline.setOnClickListener(this::offlineMode);
    }

    private void offlineMode(View view) {
    }

    private void login(View view) {
        btnLogin.setClickable(false);
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email and Password must not be empty !", Toast.LENGTH_SHORT).show();
            btnLogin.setClickable(true);
            return;
        }
        Toast.makeText(this,"Logging in ...", Toast.LENGTH_LONG).show();
        LoginDTO user = new LoginDTO(email,password);
        try {
            ApiService.getUserApiEndpoint()
                    .login(user)
                    .enqueue(new Callback<TokenDTO>() {
                        @Override
                        public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                            TokenDTO dto = response.body();
                            AppConfig.token = dto.getToken();
                            String email = dto.getEmail();
                            String lastEmail = sharedPreferences.getString("lastEmail", "");
                            Log.d("debug login ok",email+"|"+lastEmail);
                            if(!email.equals(lastEmail) && !lastEmail.isBlank()){
//                              Todo
//                              warn change account will lost all local change
//                              if localchanges > 0, warn all changes apply to this acc

                                editor.putString("lastEmail", email);
                                editor.apply();
                                db.clearData();
                            }
                            Log.d("debug login ok token", AppConfig.token);
                            syncCategory();
                            syncTask();
                            AppConfig.isOffline = false;
                        }

                        @Override
                        public void onFailure(Call<TokenDTO> call, Throwable throwable) {
                            btnLogin.setClickable(true);
                            Toast.makeText(context,"Login fail: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.d("debug login api exception", e.getMessage());
            btnLogin.setClickable(true);

            Toast.makeText(this,"Login went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void syncCategory() {
        try {
            ApiService.getCategoryApiEndpoint()
                    .getAllCategory()
                    .enqueue(new Callback<ListResponse<Category>>() {
                        @Override
                        public void onResponse(Call<ListResponse<Category>> call, Response<ListResponse<Category>> response) {
                            List<Category> syncedData = response.body().getData();
                            db.syncCategory(syncedData);
                            Log.d("debug sync category ok", "synced");
                        }

                        @Override
                        public void onFailure(Call<ListResponse<Category>> call, Throwable throwable) {
                            btnLogin.setClickable(true);
                            Log.d("debug sync category fail", throwable.getMessage());
                            Toast.makeText(context,"Sync category fail: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.d("debug sync category exception", e.getMessage());
            btnLogin.setClickable(true);
            Toast.makeText(this,"Sync category went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void syncTask(){
        List<TaskDTO> userTasks = db.getAllLocalTask();
        try {
            ApiService.getTaskApiEndpoint()
                    .updateServer(userTasks)
                    .enqueue(new Callback<List<Task>>() {
                        @Override
                        public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                            Log.d("debug sync task ok",response.message());
                            List<Task> syncedData = response.body();
                            Log.d("debug sync task ok", response.body().size()+"");
                            db.syncTask(syncedData);
                            Log.d("debug sync api ok", syncedData.toString());
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            btnLogin.setClickable(true);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<List<Task>> call, Throwable throwable) {
                            btnLogin.setClickable(true);
                            Log.d("debug sync api fail", throwable.getMessage());
                            Toast.makeText(context,"Sync fail: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.d("debug sync api exception", e.getMessage());
            btnLogin.setClickable(true);
            Toast.makeText(this,"Sync data went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindingView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtOffline = findViewById(R.id.txtOffline);
        btnLogin = findViewById(R.id.btnLogin);
    }


}