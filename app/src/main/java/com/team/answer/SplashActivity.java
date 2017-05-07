package com.team.answer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.team.answer.activities.MainActivity;

public class SplashActivity extends AppCompatActivity {

    Runnable run;
    Handler handler = new Handler();
    public static AppCompatActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity=this;
        run = new Runnable() {

            @Override

            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                finish();
            }
        };

        handler.postDelayed(run, 2000);

    }
}
