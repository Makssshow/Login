package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Objects.Role;
import com.example.login.Objects.RolePost;
import com.example.login.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button login;
    EditText email, password;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
       checkToken();

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        login = findViewById(R.id.logIn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login.setOnClickListener(view -> {
            if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                RolePost obj = new RolePost(email.getText().toString(), password.getText().toString());
                NetworkService.getInstance()
                        .getJsonAPI()
                        .postRole(obj)
                        .enqueue(new Callback<Role>() {
                            @Override
                            public void onResponse(Call<Role> call, Response<Role> response) {
                                Role current = response.body();
                                if (current != null) {
                                    editor = pref.edit();
                                    editor.putString("token", current.getId());
                                    editor.apply();
                                    startActivity();
                                }
                            }

                            @Override
                            public void onFailure(Call<Role> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Fill fields", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void checkToken() {
        if (!pref.getString("token", "").isEmpty()) {
            NetworkService.getInstance().getJsonAPI().getRoleById(pref.getString("token", "")).enqueue(new Callback<Role>() {
                @Override
                public void onResponse(Call<Role> call, Response<Role> response) {
                    Role current = response.body();
                    if (current != null) {
                        startActivity();
                    }
                }

                @Override
                public void onFailure(Call<Role> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void startActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}