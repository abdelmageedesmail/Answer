package com.team.answer.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.analytics.FirebaseAnalytics;
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
import com.team.answer.adapter.TeamAdapter;
import com.team.answer.models.GetUserRegisterServices;
import com.team.answer.models.Teams;
import com.team.answer.models.UtilitiesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity {


    private DatabaseReference mDatabase;

    TextView teamName, team1, team2, team3;
    Button start;
    String macAddress;
    public static boolean isDestroy;
    FirebaseDatabase database;
    DatabaseReference posts;
    private FirebaseAnalytics mFirebaseAnalytics;
    HashMap<String, Teams> results;
    ArrayList<Teams> teamsList;
    RecyclerView recyclerView;
    static String teamname;
    public static boolean isFrom;
    PrimeThread T1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        teamName = (TextView) findViewById(R.id.teamName);
        recyclerView = (RecyclerView) findViewById(R.id.teamLists);
        start = (Button) findViewById(R.id.start);
        teamsList = new ArrayList<>();
        UtilitiesClass.setFont(teamName, Home.this, 0);
        UtilitiesClass.setFont(start, Home.this, 0);


        //--------------------Get MacAddress-----------------
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();
        //------------------------------------------------------------//


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
                HashSet<Teams> teamsHashSet1 = new HashSet<>();
                teamsHashSet1.addAll(posts);
                posts.clear();
                posts.addAll(teamsHashSet1);

                for (int i = 0; i < posts.size(); i++) {
                    Teams teams1 = new Teams();
                    String teamId = posts.get(i).getTeamId();
                    if (teamId.equals("1")) {
                        teamname = "الفريق الاصفر";
                    } else if (teamId.equals("2")) {
                        teamname = "الفريق الاحمر";
                    } else if (teamId.equals("3")) {
                        teamname = "الفريق الاخضر";
                    } else if (teamId.equals("4")) {
                        teamname = "الفريق الازرق";
                    }

                    teams1.setTeamName(teamname);
                    teams1.setTeamId(teamId);
                    teamsList.add(teams1);
                }



                TeamAdapter teamAdapter = new TeamAdapter(Home.this, teamsList);

                recyclerView.setAdapter(teamAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.VERTICAL, false));
                teamAdapter.notifyDataSetChanged();
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

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, teams.getTeamId());
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, teams.getTeamName());
        // bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

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


//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(30000);
//                } catch (InterruptedException e) {
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                });
//            }
//        };
//        thread.start();
//
//        new CountDownTimer(1000, 100) {
//
//            public void onTick(long millisUntilFinished) {
//                // implement whatever you want for every tick
//            }
//
//            public void onFinish() {
//
//            }
//        }.start();
        T1=new PrimeThread();
        T1.start();

                class CheckUpdate extends TimerTask {
            public void run() {
                if (isFrom){
                    T1.stopRunning();
                    isFrom=false;
                }
            }
        }

// And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(new CheckUpdate(), 0, 5000);
    }

    class PrimeThread extends Thread {

        boolean running = false;

        public void run() {
            running = true;

            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (teamsList.size() < 4) {
                     //   Log.e("waiting", "waiting...");
                        startActivity(new Intent(Home.this, GameHome.class));
                        finish();
                        interrupt();
                        stopRunning();
                        isFrom = true;
                    } else {

                        startActivity(new Intent(Home.this, GameHome.class));
                        finish();
                        interrupt();
                        stopRunning();
                        isFrom = true;
                    }
                }
            });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void stopRunning() {
            running = false;
        }
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
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "" + databaseError.toException());
            }
        });
    }
}
