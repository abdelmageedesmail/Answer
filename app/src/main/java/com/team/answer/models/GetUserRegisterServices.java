package com.team.answer.models;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.team.answer.SplashActivity;
import com.team.answer.activities.GameHome;
import com.team.answer.activities.StartGame;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Abdelmageed on 9/6/2016.
 */
public class GetUserRegisterServices extends IntentService {
    SharedPreferences sh;
    String macAddress;
    public GetUserRegisterServices() {
        super("GetUserRegisterServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (GameHome.isDestroy){
//
//            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wInfo = wifiManager.getConnectionInfo();
//            macAddress = wInfo.getMacAddress();
//
//            StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://ahmedgame.comeze.com/game/index.php/question/online", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("response",response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("errorResponse",""+error.getMessage());
//                }
//            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String,String> param=new HashMap<>();
//                    param.put("mac_address",macAddress);
//                    param.put("team_id",String.valueOf(StartGame.frmButton));
//                    param.put("state","0");
//                    return param;
//                }
//            };
//            Volley.newRequestQueue(SplashActivity.activity).add(stringRequest);
//        }

    }
}
