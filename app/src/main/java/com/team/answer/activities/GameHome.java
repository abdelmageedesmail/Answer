package com.team.answer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.team.answer.R;
import com.team.answer.fragment.RoomFragment;
import com.team.answer.models.GetUserRegisterServices;

import java.util.HashMap;
import java.util.Map;

public class GameHome extends AppCompatActivity {

    Fragment fragment;
    String macAddress;
    public static boolean isDestroy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_home);

        sendMacAddress();


        //----------- Real Time webSocket Pusher------------------


        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("8e7c7fb44f895ef6e2e1", options);

        Channel channel = pusher.subscribe("game");

        channel.bind("try", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.e("data","..."+data);
            }
        });

        pusher.connect();


        //--------------------Get MacAddress-----------------
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();
        //------------------------------------------------------------//


        fragment=new RoomFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerLayout, fragment);
        fragmentTransaction.commit();

    }

    private void sendMacAddress(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://ahmedgame.comeze.com/game/index.php/question/online", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errorResponse",""+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("mac_address",macAddress);
                param.put("team_id",String.valueOf(StartGame.frmButton));
                param.put("state","1");
                return param;
            }
        };
        Volley.newRequestQueue(GameHome.this).add(stringRequest);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy=true;
        startService(new Intent(GameHome.this, GetUserRegisterServices.class));
    }
}
