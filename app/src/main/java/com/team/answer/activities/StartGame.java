package com.team.answer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team.answer.R;
import com.team.answer.models.UtilitiesClass;

public class StartGame extends AppCompatActivity implements View.OnClickListener{

    public static int frmButton;
    Button yellowTeam,redTeam,greenTeam,blueTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        intializeWidget();
    }

    private void intializeWidget(){
        yellowTeam=(Button) findViewById(R.id.yellowTeam);
        redTeam=(Button) findViewById(R.id.redTeam);
        greenTeam=(Button) findViewById(R.id.greenTeam);
        blueTeam=(Button) findViewById(R.id.blueTeam);

        yellowTeam.setOnClickListener(this);
        redTeam.setOnClickListener(this);
        greenTeam.setOnClickListener(this);
        blueTeam.setOnClickListener(this);

        UtilitiesClass.setFont(yellowTeam,StartGame.this,0);
        UtilitiesClass.setFont(redTeam,StartGame.this,0);
        UtilitiesClass.setFont(greenTeam,StartGame.this,0);
        UtilitiesClass.setFont(blueTeam,StartGame.this,0);
    }

    @Override
    public void onClick(View v) {
     int id=v.getId();
        Intent intent = new Intent(StartGame.this, Home.class);

        switch (id){
            case R.id.yellowTeam:
                frmButton=1;
                intent.putExtra("team_name",yellowTeam.getText().toString());
                intent.putExtra("id",1);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                break;
            case R.id.redTeam:
                frmButton=2;
                intent.putExtra("team_name",redTeam.getText().toString());
                intent.putExtra("id",2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                break;
            case R.id.greenTeam:
                frmButton=3;
                intent.putExtra("team_name",greenTeam.getText().toString());
                intent.putExtra("id",3);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                break;
            case R.id.blueTeam:
                frmButton=4;
                intent.putExtra("team_name",blueTeam.getText().toString());
                intent.putExtra("id",4);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                break;
        }
    }
}
