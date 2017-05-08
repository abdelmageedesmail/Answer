package com.team.answer.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.team.answer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by javawy on 5/8/17.
 */

public class WinnerFragment extends DialogFragment{
    private ImageView mImg;
    private TextView mText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.winner_team, null);
        mImg = (ImageView) view.findViewById(R.id.imageView);
        mText = (TextView) view.findViewById(R.id.textView);
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(mImg);
        Glide.with(getActivity()).load(R.raw.winer).into(target);
        getWinner();


        return builder.create();
    }

    private void getWinner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ahmedgame.comeze.com/game/index.php/question/get_winner_team", new Response.Listener<String>() {
            public String high_score;
            public String team_id;
            String mWinner;


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray winner = jsonObject.getJSONArray("winner");
                    for (int i = 0; i <winner.length() ; i++) {
                        JSONObject jsonObject1 = winner.getJSONObject(i);
                         team_id = jsonObject1.getString("team_id");
                         high_score = jsonObject1.getString("high_score");

                    }
                    switch (team_id) {
                        case "1":mWinner="yellow team";
                            break;
                        case "2":mWinner="red team";
                            break;
                        case "3":mWinner="green team";
                            break;
                        case "4":mWinner="blue team";
                            break;
                    }
                    mText.setText("The winner team is "+winner+" has "+high_score);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
