package com.lcj.recycler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            sleep(800);
        } catch (Exception e) {

        }

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);

        finish();

    }
}
