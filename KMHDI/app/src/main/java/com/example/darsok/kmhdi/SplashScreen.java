package com.example.darsok.kmhdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread() {
            public void run(){
                try {
                    sleep(3000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashScreen.this,MenuUtama.class));
                    finish();
                }
            }
        };
        thread.start();



    }
}
