package com.team.answer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team.answer.R;
import com.team.answer.models.UtilitiesClass;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button startPlay,aboutGame,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeWidget();
    }

    private void intializeWidget(){
        startPlay=(Button) findViewById(R.id.startPlay);
        aboutGame=(Button) findViewById(R.id.aboutGame);
        exit=(Button) findViewById(R.id.logOut);
        startPlay.setOnClickListener(this);

        UtilitiesClass.setFont(startPlay,MainActivity.this,0);
        UtilitiesClass.setFont(aboutGame,MainActivity.this,0);
        UtilitiesClass.setFont(exit,MainActivity.this,0);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.startPlay:

                startActivity(new Intent(MainActivity.this,StartGame.class));
                overridePendingTransition(R.anim.slide_in_likes_counter, R.anim.slide_out_likes_counter);
                finish();
                break;

        }
    }
}
