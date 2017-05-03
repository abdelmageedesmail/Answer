package com.team.answer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team.answer.R;
import com.team.answer.models.Teams;
import com.team.answer.models.UtilitiesClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelmageed on 03/05/17.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyHolder> {
    Context context;
    ArrayList<Teams> list;
    public TeamAdapter(Context context,ArrayList<Teams> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.team_item,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.team.setText(list.get(position).getTeamName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView team;
        public MyHolder(View itemView) {
            super(itemView);
            team=(TextView) itemView.findViewById(R.id.team1);
            UtilitiesClass.setFont(team,context,0);
        }
    }
}
