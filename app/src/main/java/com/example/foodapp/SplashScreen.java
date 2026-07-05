package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    TextView appName;
    TextView powerdby;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        logo=findViewById(R.id.logo);
        appName=findViewById(R.id.appName);
        powerdby=findViewById(R.id.poweredby);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splashscreenanimation);
        logo.startAnimation(animation);
        appName.startAnimation(animation);
        powerdby.startAnimation(animation);
    }
}