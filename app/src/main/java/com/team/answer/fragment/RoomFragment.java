package com.team.answer.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.team.answer.R;
import com.team.answer.activities.GameHome;
import com.team.answer.activities.StartGame;
import com.team.answer.models.QueData;
import com.team.answer.models.UtilitiesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelmageed on 09/04/17.
 */

public class RoomFragment extends Fragment {

    TextView txtTurn,title,score1,score2,score3,score4,mQuestion;
    Button answer1,answer2,answer3,answer4;
    private static String SERVICE_URL="http://ahmedgame.comeze.com/game/index.php/question/question_and_answers";
    public ArrayList<QueData> mData=new ArrayList<>();

    private ProgressBar mProgressbar;
    private TextView mTimer;
    private int scoret1,scoret2,scoret3,scoret4;
    private int i=0;

    public RoomFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.room_fragment, container, false);
        //txtTurn = (TextView) v.findViewById(R.id.turn);
        title = (TextView) v.findViewById(R.id.title);
        score1 = (TextView) v.findViewById(R.id.score1);
        score2 = (TextView) v.findViewById(R.id.score2);
        score3 = (TextView) v.findViewById(R.id.score3);
        score4 = (TextView) v.findViewById(R.id.score4);
        mQuestion = (TextView) v.findViewById(R.id.question);
        answer1 = (Button) v.findViewById(R.id.answer1);
        answer2 = (Button) v.findViewById(R.id.answer2);
        answer3 = (Button) v.findViewById(R.id.answer3);
        answer4 = (Button) v.findViewById(R.id.answer4);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progressBar);
        mTimer = (TextView) v.findViewById(R.id.timer);
       // UtilitiesClass.setFont(txtTurn, getActivity(), 0);
        UtilitiesClass.setFont(title, getActivity(), 0);
        UtilitiesClass.setFont(score1, getActivity(), 0);
        UtilitiesClass.setFont(score2, getActivity(), 0);
        UtilitiesClass.setFont(score3, getActivity(), 0);
        UtilitiesClass.setFont(score4, getActivity(), 0);
        UtilitiesClass.setFont(mQuestion, getActivity(), 0);
        UtilitiesClass.setFont(answer1, getActivity(), 0);
        UtilitiesClass.setFont(answer2, getActivity(), 0);
        UtilitiesClass.setFont(answer3, getActivity(), 0);
        UtilitiesClass.setFont(answer4, getActivity(), 0);
        UtilitiesClass.setFont(mTimer,getActivity(),0);
         sendRequest(getActivity(), SERVICE_URL, StartGame.frmButton);
        long starter=30000;
        //System.out.println("size is:"+lst.size());
       // setWidgets(mData,starter);

        return v;
    }



    private void sendRequest(final FragmentActivity activity, String serviceUrl, int frmButton) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serviceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String encode = URLEncoder.encode(response, "ISO-8859-1");
                    response = URLDecoder.decode(encode, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                setWidgets(response);
             /*   try {
                    //   Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray questions = jsonObject.getJSONArray("question");


                    for (int i = 0; i < questions.length(); i++) {
                        JSONObject jsonObject1 = questions.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String question = jsonObject1.getString("question_text");
                        String choice1 = jsonObject1.getString("choice1");
                        String choice2 = jsonObject1.getString("choice2");
                        String choice3 = jsonObject1.getString("choice3");
                        String choice4 = jsonObject1.getString("choice4");
                        String answer = jsonObject1.getString("correct_choice");
                        String level = jsonObject1.getString("level");
                        Log.d("question: ", question + "\n" + "answer: " + answer);
                        QueData queData = new QueData(question, new String[]{choice1, choice2, choice3, choice4}, answer, level);
                        mData.add(i, queData);
                        setWidgets(response);


                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(activity).add(stringRequest);

    }
//final ArrayList<QueData> mData,   long starter
    public void setWidgets(String response) {
        
        try {
            //   Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray questions = jsonObject.getJSONArray("question");


            for (int i = 0; i < questions.length(); i++) {
                JSONObject jsonObject1 = questions.getJSONObject(i);
                String id = jsonObject1.getString("id");
                String question = jsonObject1.getString("question_text");
                String choice1 = jsonObject1.getString("choice1");
                String choice2 = jsonObject1.getString("choice2");
                String choice3 = jsonObject1.getString("choice3");
                String choice4 = jsonObject1.getString("choice4");
                String answer = jsonObject1.getString("correct_choice");
                String level = jsonObject1.getString("level");
                Log.d("question: ", question + "\n" + "answer: " + answer);
                QueData queData = new QueData(question, new String[]{choice1, choice2, choice3, choice4}, answer, level);
                mData.add(i, queData);



            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("size is:"+mData.size());
       // int i=0;



            QueData queData = mData.get(i);
            String question = queData.getQuestion();
            mQuestion.setText(question);
            answer1.setText(queData.getChoices()[0]);
            answer2.setText(queData.getChoices()[1]);
            answer3.setText(queData.getChoices()[2]);
            answer4.setText(queData.getChoices()[3]);
            title.setText("المستوى: "+queData.getLevel());
            final String correctAnswer = queData.getAnswer();

            final CountDownTimer timer = new CountDownTimer(30000, 1000) {


                public boolean status = true;


                @Override
                public void onTick(long millisUntilFinished) {

                    mTimer.setText(millisUntilFinished / 1000 + "");
                    answer4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            status = false;
                            answer1.setEnabled(status);
                            answer3.setEnabled(status);
                            answer2.setEnabled(status);
                            answer4.setEnabled(status);
                            mTimer.setVisibility(View.INVISIBLE);
                            mProgressbar.setVisibility(View.INVISIBLE);
                            checkAnswer(correctAnswer, answer4.getText().toString(), answer4);
                            onFinish();
                        }
                    });
                    answer3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            status = false;
                            answer1.setEnabled(status);
                            answer2.setEnabled(status);
                            answer3.setEnabled(status);
                            answer4.setEnabled(status);
                            mTimer.setVisibility(View.INVISIBLE);
                            mProgressbar.setVisibility(View.INVISIBLE);
                            checkAnswer(correctAnswer, answer3.getText().toString(), answer3);
                            onFinish();
                        }
                    });
                    answer2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            status = false;
                            answer1.setEnabled(status);
                            answer2.setEnabled(status);
                            answer3.setEnabled(status);
                            answer4.setEnabled(status);
                            mTimer.setVisibility(View.INVISIBLE);
                            mProgressbar.setVisibility(View.INVISIBLE);
                            checkAnswer(correctAnswer, answer2.getText().toString(), answer2);
                            onFinish();
                        }
                    });
                    answer1.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {

                            checkAnswer(correctAnswer, answer1.getText().toString(), answer1);
                            onFinish();
                        }
                    });


                }

                @Override
                public void onFinish() {
                    status = false;
                    answer1.setEnabled(status);
                    answer2.setEnabled(status);
                    answer3.setEnabled(status);
                    answer4.setEnabled(status);
                    mTimer.setVisibility(View.INVISIBLE);
                    mProgressbar.setVisibility(View.INVISIBLE);
                    cancel();
                    i++;
                    status = true;
                    System.out.println(i);

                }


            };

            timer.start();
        }





    private void checkAnswer(String correctAnswer,String user_answer,Button ans) {

        if (user_answer.equalsIgnoreCase(correctAnswer)){
            ans.setBackgroundColor(getResources().getColor(R.color.correct));
           int team= StartGame.frmButton ;
            if (team==1){
                scoret1++;
                score1.setText(""+scoret1);


            }
            else if (team==2){
                scoret2++;
                score2.setText(""+scoret2);
            }
            else if (team==3){
                scoret3++;
                score3.setText(""+scoret3);
            }
            else if(team==4){
                scoret4++;
                score4.setText(""+scoret4);
            }
        }
        else {
            ans.setBackgroundColor(getResources().getColor(R.color.wrong));

        }
    }
}
