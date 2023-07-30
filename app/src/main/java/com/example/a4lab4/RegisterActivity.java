package com.example.a4lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lab4.Interface.ApiService;
import com.example.a4lab4.Model.User;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout textInputLayoutRName, textInputLayoutREmail, textInputLayoutRPassword;
    TextView txtLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textInputLayoutRName = findViewById(R.id.textInputLayoutRName);
        textInputLayoutREmail = findViewById(R.id.textInputLayoutREmail);
        textInputLayoutRPassword = findViewById(R.id.textInputLayoutRPassword);
        txtLogin = findViewById(R.id.txtLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textInputLayoutRName.getEditText().getText().toString();
                String email = textInputLayoutREmail.getEditText().getText().toString();
                String password = textInputLayoutRPassword.getEditText().getText().toString();
                submit(name, email, password);
            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void submit(String name , String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.188.170:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        ServerRequest request = new ServerRequest();
        request.setOperation("register");
        request.setUser(user);

        Call<ServerRespone> response = apiService.operation(request);
        response.enqueue(new Callback<ServerRespone>() {
            @Override
            public void onResponse(Call<ServerRespone> call, Response<ServerRespone> response) {
                ServerRespone resp = response.body();
                if (resp.getResult().equals("success")){
                    Toast.makeText(RegisterActivity.this, ""+resp.getMessage(), Toast.LENGTH_SHORT).show();
                    resetForm();
                }
            }

            @Override
            public void onFailure(Call<ServerRespone> call, Throwable t) {

            }
        });

    }

    private void resetForm() {
        textInputLayoutRName.getEditText().setText("");
        textInputLayoutREmail.getEditText().setText("");
        textInputLayoutRPassword.getEditText().setText("");

    }
}