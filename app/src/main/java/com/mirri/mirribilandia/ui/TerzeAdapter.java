package com.mirri.mirribilandia.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mirri.mirribilandia.R;
import java.util.List;

import com.mirri.mirribilandia.ui.gironi.Team;

/**
 * Created by Mattia on 09/06/2018.
 */


public class TerzeAdapter extends ArrayAdapter<Team> {


    public TerzeAdapter(Context context, List<Team> thirdTeams) {
        super(context, 0, thirdTeams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team t = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_terze, parent, false);
        }
        // Lookup view for data population
        TextView nomeSquadra = (TextView) convertView.findViewById(R.id.terzaSq);
        nomeSquadra.setText(t.getName());
        if(position>13) {
            nomeSquadra.setBackgroundResource(R.drawable.button_border_white);
        } else {
            nomeSquadra.setBackgroundResource(R.drawable.button_border_green);
        }
        TextView win = (TextView) convertView.findViewById(R.id.winTerzaSq);
        win.setText(""+t.getWins());
        TextView draw = (TextView) convertView.findViewById(R.id.drawTerzaSq);
        draw.setText(""+t.getDraws());
        TextView lose = (TextView) convertView.findViewById(R.id.loseTerzaSq);
        lose.setText(""+t.getLoses());
        TextView goal = (TextView) convertView.findViewById(R.id.goalTerzaSq);
        goal.setText(""+t.getGoalDone());
        TextView dr = (TextView) convertView.findViewById(R.id.drTerzaSq);
        dr.setText(""+t.getGoalDiff());
        TextView punti = (TextView) convertView.findViewById(R.id.puntiTerzaSq);
        punti.setText(""+t.getPoints());

        // Return the completed view to render on screen
        return convertView;
    }
}