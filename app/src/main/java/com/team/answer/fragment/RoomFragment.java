package com.team.answer.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.team.answer.R;
import com.team.answer.activities.GameHome;
import com.team.answer.activities.Home;
import com.team.answer.activities.StartGame;
import com.team.answer.models.CustomHorizntalLayoutManager;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by abdelmageed on 09/04/17.
 */

public class RoomFragment extends Fragment {

    TextView txtTurn, title, score1, score2, score3, score4, mQuestion;
    Button answer1, answer2, answer3, answer4;
    private static String SERVICE_URL = "http://ahmedgame.comeze.com/game/index.php/question/question_and_answers";
    public ArrayList<QueData> mData = new ArrayList<>();
    ImageView imageTrue;
    private ProgressBar mProgressbar;
    private TextView mTimer;
    private int scoret1, scoret2, scoret3, scoret4;
    private int i = 0;
    public static boolean isCorrect;
    String correctAnswer;
    ArrayList<QueData> arrayList;
    PrimeThread T1;
    public RoomFragment() {

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
        imageTrue=(ImageView) v.findViewById(R.id.imageTrue);
        UtilitiesClass.setFont(mQuestion, getActivity(), 0);
        UtilitiesClass.setFont(answer1, getActivity(), 0);
        UtilitiesClass.setFont(answer2, getActivity(), 0);
        UtilitiesClass.setFont(answer3, getActivity(), 0);
        UtilitiesClass.setFont(answer4, getActivity(), 0);

        mProgressbar = (ProgressBar) v.findViewById(R.id.progressBar);
        mTimer = (TextView) v.findViewById(R.id.timer);
        // UtilitiesClass.setFont(txtTurn, getActivity(), 0);
        UtilitiesClass.setFont(title, getActivity(), 0);
        UtilitiesClass.setFont(score1, getActivity(), 0);
        UtilitiesClass.setFont(score2, getActivity(), 0);
        UtilitiesClass.setFont(score3, getActivity(), 0);
        UtilitiesClass.setFont(score4, getActivity(), 0);
        arrayList = new ArrayList<>();
        UtilitiesClass.setFont(mTimer, getActivity(), 0);
        sendRequest(getActivity(), SERVICE_URL, StartGame.frmButton);


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
                 T1=new PrimeThread();
                T1.start();


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
            JSONObject jsonObject = new JSONObject(response);
            JSONArray questions = jsonObject.getJSONArray("question");

            for (int i = 0; i < questions.length(); i++) {
                QueData queData = new QueData();
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
                queData.setQuestion(question);
                queData.setChoice1(choice1);
                queData.setChoice2(choice2);
                queData.setChoice3(choice3);
                queData.setChoice4(choice4);
                queData.setAnswer(answer);
                queData.setLevel(level);

                if (answer.equals("الإختيار الأول")){
                    correctAnswer=answer1.getText().toString();
                }else if (answer.equals("الإختيار الثاني")){
                    correctAnswer=answer2.getText().toString();
                }else if (answer.equals("الإختيار الثالث")){
                    correctAnswer=answer3.getText().toString();
                }else if (answer.equals("الإختيار الرابع")){
                    correctAnswer=answer4.getText().toString();
                }
                arrayList.add(queData);


//                SharedPreferences mPrefs = getActivity().getSharedPreferences("warnings",MODE_PRIVATE);
//                SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                Gson gson = new Gson();
//                String ja = gson.toJson(mData);
//                prefsEditor.putString("MyObject", ja);
//                prefsEditor.apply();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // System.out.println("size is:"+mData.size());
        // int i=0;

//            QueData queData = mData.get(i);
//            String question = queData.getQuestion();
//            mQuestion.setText(question);
//            answer1.setText(queData.getChoices()[0]);
//            answer2.setText(queData.getChoices()[1]);
//            answer3.setText(queData.getChoices()[2]);
//            answer4.setText(queData.getChoices()[3]);
//            title.setText("المستوى: "+queData.getLevel());
//            correctAnswer = queData.getAnswer();
//
//            final CountDownTimer timer = new CountDownTimer(30000, 1000) {
//
//
//                public boolean status = true;
//
//
//                @Override
//                public void onTick(long millisUntilFinished) {
//
//                    mTimer.setText(millisUntilFinished / 1000 + "");
//                    answer4.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            status = false;
//                            answer1.setEnabled(status);
//                            answer3.setEnabled(status);
//                            answer2.setEnabled(status);
//                            answer4.setEnabled(status);
//                            mTimer.setVisibility(View.INVISIBLE);
//                            mProgressbar.setVisibility(View.INVISIBLE);
//                            checkAnswer(correctAnswer, answer4.getText().toString(), answer4);
//                            onFinish();
//                        }
//                    });
//                    answer3.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            status = false;
//                            answer1.setEnabled(status);
//                            answer2.setEnabled(status);
//                            answer3.setEnabled(status);
//                            answer4.setEnabled(status);
//                            mTimer.setVisibility(View.INVISIBLE);
//                            mProgressbar.setVisibility(View.INVISIBLE);
//                            checkAnswer(correctAnswer, answer3.getText().toString(), answer3);
//                            onFinish();
//                        }
//                    });
//                    answer2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            status = false;
//                            answer1.setEnabled(status);
//                            answer2.setEnabled(status);
//                            answer3.setEnabled(status);
//                            answer4.setEnabled(status);
//                            mTimer.setVisibility(View.INVISIBLE);
//                            mProgressbar.setVisibility(View.INVISIBLE);
//                            checkAnswer(correctAnswer, answer2.getText().toString(), answer2);
//                            onFinish();
//                        }
//                    });
//                    answer1.setOnClickListener(new View.OnClickListener() {
//
//
//                        @Override
//                        public void onClick(View v) {
//
//                            checkAnswer(correctAnswer, answer1.getText().toString(), answer1);
//                            onFinish();
//                        }
//                    });
//
//
//                }
//
//                @Override
//                public void onFinish() {
//                    status = false;
//                    answer1.setEnabled(status);
//                    answer2.setEnabled(status);
//                    answer3.setEnabled(status);
//                    answer4.setEnabled(status);
//                    mTimer.setVisibility(View.INVISIBLE);
//                    mProgressbar.setVisibility(View.INVISIBLE);
//                    cancel();
//                    i++;
//                    status = true;
//                    System.out.println(i);
//
//                }
//
//
//            };
//
//            timer.start();
//
//
    }


    class PrimeThread extends Thread {
        int i;
        boolean running = false;

        public void run() {
            running = true;

            for (i = 0; i < arrayList.size(); i++) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mQuestion.setText(arrayList.get(i).getQuestion());
                        answer1.setText(arrayList.get(i).getChoice1());
                        answer2.setText(arrayList.get(i).getChoice2());
                        answer3.setText(arrayList.get(i).getChoice3());
                        answer4.setText(arrayList.get(i).getChoice4());
                        title.setText("المرحله"+arrayList.get(i).getLevel());

                        new CountDownTimer(30000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                mTimer.setText(""+millisUntilFinished / 1000);
                            }

                            public void onFinish() {

                            }
                        }.start();
                        answer1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (arrayList.get(i).getAnswer().equals(correctAnswer)){
                                    checkAnswer(arrayList.get(i).getAnswer(),answer1.getText().toString(),answer1);
                                    imageTrue.setImageResource(R.drawable.correct);
                                    interrupt();
                                }else {
                                    imageTrue.setImageResource(R.drawable.wrong);
                                }

                            }
                        });

                        answer2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (arrayList.get(i).getAnswer().equals(correctAnswer)){
                                    checkAnswer(arrayList.get(i).getAnswer(),answer2.getText().toString(),answer2);
                                    imageTrue.setImageResource(R.drawable.correct);
                                    interrupt();
                                }else {
                                    imageTrue.setImageResource(R.drawable.wrong);
                                }
                            }
                        });

                        answer3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (arrayList.get(i).getAnswer().equals(correctAnswer)){
                                    checkAnswer(arrayList.get(i).getAnswer(),answer3.getText().toString(),answer3);
                                    imageTrue.setImageResource(R.drawable.correct);
                                }else {
                                    imageTrue.setImageResource(R.drawable.wrong);
                                }
                            }
                        });
                        answer4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (arrayList.get(i).getAnswer().equals(correctAnswer)){
                                    checkAnswer(arrayList.get(i).getAnswer(),answer4.getText().toString(),answer4);
                                    imageTrue.setImageResource(R.drawable.correct);
                                }else {
                                    imageTrue.setImageResource(R.drawable.wrong);
                                }
                            }
                        });

                    }
                });

                try {
                    Thread.sleep(30000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


//            i++;


            // }
        }



        public void stopRunning() {
            running = false;
        }
    }

//    class PrimeThread extends Thread {
//
//        boolean running = false;
//
//
//        public boolean status = true;
//
//        public void run() {
//            running = true;
//
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//
//                    mTimer.setText(3000 / 1000 + "");
//                    answer4.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            status = false;
//                            answer1.setEnabled(status);
//                            answer3.setEnabled(status);
//                            answer2.setEnabled(status);
//                            answer4.setEnabled(status);
//                            mTimer.setVisibility(View.INVISIBLE);
//                            mProgressbar.setVisibility(View.INVISIBLE);
//                            checkAnswer(correctAnswer, answer4.getText().toString(), answer4);
//
//                        }
//                    });
//                    answer3.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            status = false;
//                            answer1.setEnabled(status);
//                            answer2.setEnabled(status);
//                            answer3.setEnabled(status);
//                            answer4.setEnabled(status);
//                            mTimer.setVisibility(View.INVISIBLE);
//                            mProgressbar.setVisibility(View.INVISIBLE);
//                            checkAnswer(correctAnswer, answer3.getText().toString(), answer3);
//
//                        }
//                    });
//                    answer2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            status = false;
//                            answer1.setEnabled(status);
//                            answer2.setEnabled(status);
//                            answer3.setEnabled(status);
//                            answer4.setEnabled(status);
//                            mTimer.setVisibility(View.INVISIBLE);
//                            mProgressbar.setVisibility(View.INVISIBLE);
//                            checkAnswer(correctAnswer, answer2.getText().toString(), answer2);
//
//                        }
//                    });
//                    answer1.setOnClickListener(new View.OnClickListener() {
//
//
//                        @Override
//                        public void onClick(View v) {
//
//                            checkAnswer(correctAnswer, answer1.getText().toString(), answer1);
//
//                        }
//                });
//            }
//            });
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        public void stopRunning() {
//            running = false;
//        }
//    }

    private void checkAnswer(String correctAnswer, String user_answer, Button ans) {

        if (user_answer.equalsIgnoreCase(correctAnswer)) {
            isCorrect = true;
            imageTrue.setImageResource(R.drawable.correct);
            int team = StartGame.frmButton;
            if (team == 1) {
                scoret1++;
                score1.setText("" + scoret1);


            } else if (team == 2) {
                scoret2++;
                score2.setText("" + scoret2);
            } else if (team == 3) {
                scoret3++;
                score3.setText("" + scoret3);
            } else if (team == 4) {
                scoret4++;
                score4.setText("" + scoret4);
            }
        } else {
            // fault answer here
            imageTrue.setImageResource(R.drawable.wrong);
        }
    }


}
