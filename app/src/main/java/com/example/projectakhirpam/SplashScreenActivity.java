package com.example.projectakhirpam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        Thread welcomeThread = new Thread() {

            @Override
            public void run(){
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e){

                } finally {
                    startActivity(new Intent(SplashScreenActivity.this, HomepageActivity.class));
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
