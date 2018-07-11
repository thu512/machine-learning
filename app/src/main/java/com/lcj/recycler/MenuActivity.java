package com.lcj.recycler;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lcj.recycler.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.setActivity(this);

    }

    public void goMl(View v){
        Intent intent = new Intent(this, MlActivity.class);
        startActivity(intent);
    }

    public void goSearch(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logout(View v){

    }
}
