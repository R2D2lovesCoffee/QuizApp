package com.example.aeroz.quizzapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoleplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roleplay);

        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fireActivity(new SigninActivity());
//            }
//        });
    }
    public void fireActivity(Activity a){
        Intent intent = new Intent(getBaseContext(),a.getClass());
        startActivity(intent);
    }
    
}