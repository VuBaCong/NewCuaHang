package com.example.cuahangonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cuahangonline.R;
import com.example.cuahangonline.fragment.LoginFragment;
import com.example.cuahangonline.fragment.WelcomeFragment;
import com.example.cuahangonline.utils.data.PrefConfig;
import com.example.cuahangonline.utils.service.ApiClient;
import com.example.cuahangonline.utils.service.ApiInterface;

public class LoginActivity extends AppCompatActivity {

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefConfig = new PrefConfig(getApplicationContext());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        setupView();
    }

    private void setupView() {
        if (findViewById(R.id.fragment_container) != null) {
            if (prefConfig.readLoginStatus()) {
                if (prefConfig.readCheckAdmin() == 0) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
/*                    Intent intent=new Intent(getApplicationContext(),AdminManagerAcitivity.class);
                    startActivity(intent);
                    finish();*/
                }

            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WelcomeFragment()).commit();
            }
        }
    }
}