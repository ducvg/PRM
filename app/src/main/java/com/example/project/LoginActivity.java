package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.Service.ApiService;
import com.example.project.main.AppConfig;
import com.example.project.model.ServiceModel.LoginDTO;
import com.example.project.model.ServiceModel.TokenDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;
    private Button btnLogin;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        context = this;
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::login);
        btnRegister.setOnClickListener(this::register);
    }

    private void register(View view) {

    }

    private void login(View view) {
        btnLogin.setClickable(false);
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email and Password must not be empty !", Toast.LENGTH_SHORT).show();
            return;
        }
        LoginDTO user = new LoginDTO(email,password);
        try {
            ApiService.getUserApiEndpoint()
                    .login(user)
                    .enqueue(new Callback<TokenDTO>() {

                        @Override
                        public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                            AppConfig.token = response.body().getToken();
                            Log.d("debug login ok", AppConfig.token);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            btnLogin.setClickable(true);
                            finish();
                            Log.d("debug login api", AppConfig.token);
                        }

                        @Override
                        public void onFailure(Call<TokenDTO> call, Throwable throwable) {
                            Toast.makeText(context,"Login fail: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.d("debug login api exception", e.getMessage());
            Toast.makeText(this,"Login went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindingView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
    }


}