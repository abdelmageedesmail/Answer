package com.team.answer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.team.answer.R;
import com.team.answer.activities.StartGame;
import com.team.answer.models.UtilitiesClass;

/**
 * Created by abdelmageed on 09/04/17.
 */

public class RoomFragment extends Fragment {

    TextView txtTurn,title,score1,score2,score3,score4,question;
    Button answer1,answer2,answer3,answer4;

    public RoomFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.room_fragment,container,false);
        txtTurn=(TextView) v.findViewById(R.id.turn);
        title=(TextView) v.findViewById(R.id.title);
        score1=(TextView) v.findViewById(R.id.score1);
        score2=(TextView) v.findViewById(R.id.score2);
        score3=(TextView) v.findViewById(R.id.score3);
        score4=(TextView) v.findViewById(R.id.score4);
        question=(TextView) v.findViewById(R.id.question);
        answer1=(Button) v.findViewById(R.id.answer1);
        answer2=(Button) v.findViewById(R.id.answer2);
        answer3=(Button) v.findViewById(R.id.answer3);
        answer4=(Button) v.findViewById(R.id.answer4);

        UtilitiesClass.setFont(txtTurn,getActivity(),0);
        UtilitiesClass.setFont(title,getActivity(),0);
        UtilitiesClass.setFont(score1,getActivity(),0);
        UtilitiesClass.setFont(score2,getActivity(),0);
        UtilitiesClass.setFont(score3,getActivity(),0);
        UtilitiesClass.setFont(score4,getActivity(),0);
        UtilitiesClass.setFont(question,getActivity(),0);
        UtilitiesClass.setFont(answer1,getActivity(),0);
        UtilitiesClass.setFont(answer2,getActivity(),0);
        UtilitiesClass.setFont(answer3,getActivity(),0);
        UtilitiesClass.setFont(answer4,getActivity(),0);
        
        if (StartGame.frmButton==1){
            txtTurn.setText("دور الفريق الاصفر");
        }else if (StartGame.frmButton==2){
            txtTurn.setText("دور الفريق الاحمر");
        }else if (StartGame.frmButton==3){
            txtTurn.setText("دور الفريق الاخضر");
        }else if (StartGame.frmButton==4){
            txtTurn.setText("دور الفريق الازرق");
        }
        return v;
    }
}
