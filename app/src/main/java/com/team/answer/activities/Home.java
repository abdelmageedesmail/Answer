package com.team.answer.activities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.team.answer.R;
import com.team.answer.models.GetUserRegisterServices;
import com.team.answer.models.Teams;
import com.team.answer.models.UtilitiesClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Home extends AppCompatActivity {


    private DatabaseReference mDatabase;

    TextView teamName, team1, team2, team3;
    Button start;
    String macAddress;
    public static boolean isDestroy;
    FirebaseDatabase database;
    DatabaseReference posts;
    HashMap<String, Teams> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        teamName = (TextView) findViewById(R.id.teamName);
        team1 = (TextView) findViewById(R.id.team1);
        team2 = (TextView) findViewById(R.id.team2);
        team3 = (TextView) findViewById(R.id.team3);
        start = (Button) findViewById(R.id.start);

        UtilitiesClass.setFont(teamName, Home.this, 0);
        UtilitiesClass.setFont(team1, Home.this, 0);
        UtilitiesClass.setFont(team2, Home.this, 0);
        UtilitiesClass.setFont(team3, Home.this, 0);
        UtilitiesClass.setFont(start, Home.this, 0);


        //--------------------Get MacAddress-----------------
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();
        //------------------------------------------------------------//

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        writeNewUser(String.valueOf(StartGame.frmButton),macAddress);

        database = FirebaseDatabase.getInstance();
        posts = database.getReference("teams");
        final Teams teams = new Teams();
        teams.setTeamId(String.valueOf(StartGame.frmButton));
        teams.setMacAddress(macAddress);
        teams.setState("1");
        posts.push().setValue(teams);

        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, Teams>>() {
                    });

                }
                    List<Teams> posts = new ArrayList<>(results.values());


                Set uniqueEntries = new HashSet();
                for (Iterator iter = posts.iterator(); iter.hasNext(); ) {
                    Object element = iter.next();
                    if (!uniqueEntries.add(element)) // if current element is a duplicate,
                        iter.remove();                 // remove it
                }

                for (int i=0;i<posts.size();i++) {
                    if (posts.get(i).getTeamId().equals(String.valueOf(StartGame.frmButton))){
                        Log.e("teamId",posts.get(i).getTeamId());
                    }else {
                        if (posts.contains("2")) {
                            team1.setText("الفريق الاحمر جاهز");
                        }
                        if (posts.contains("3")) {
                            team1.setText("الفريق الاخضر جاهز");
                        }if (posts.contains("4")) {
                            team1.setText("الفريق الاصفر جاهز");
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //----------- Real Time webSocket Pusher------------------


//        PusherOptions options = new PusherOptions();
//        options.setCluster("eu");
//        Pusher pusher = new Pusher("8e7c7fb44f895ef6e2e1", options);
//
//        Channel channel = pusher.subscribe("game");
//
//        channel.bind("try", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                Log.e("data","..."+data);
//
//                try {
//                    JSONObject object=new JSONObject(data);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        pusher.connect();


        if (StartGame.frmButton == 1) {
            teamName.setText("الفريق الاصفر");
        } else if (StartGame.frmButton == 2) {
            teamName.setText("الفريق الاحمر");
        } else if (StartGame.frmButton == 3) {
            teamName.setText("الفريق الاخضر");
        } else if (StartGame.frmButton == 4) {
            teamName.setText("الفريق الازرق");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //    sendMacAddress();


    }

    private void sendMacAddress() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ahmedgame.comeze.com/game/index.php/question/online", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errorResponse", "" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("mac_address", macAddress);
                param.put("team_id", String.valueOf(StartGame.frmButton));
                param.put("state", "1");
                return param;
            }
        };
        Volley.newRequestQueue(Home.this).add(stringRequest);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isDestroy=true;
//        startService(new Intent(Home.this, GetUserRegisterServices.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        database = FirebaseDatabase.getInstance();
//        posts = database.getReference("teams");
//        Teams teams = new Teams();
//        teams.setTeamId(String.valueOf(StartGame.frmButton));
//        teams.setMacAddress(macAddress);
//        teams.setState("0");
//        posts.push().setValue(teams);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("teams").orderByChild("teamId").equalTo(String.valueOf(StartGame.frmButton));

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e( "onCancelled",""+ databaseError.toException());
            }
        });
    }
}
