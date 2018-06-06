package com.mirri.mirribilandia.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;

import java.util.ArrayList;

/**
 * Created by Mattia on 28/05/2018.
 */

public class MatchesAdapter extends ArrayAdapter<FasiFinaliContent.FasiFinaliItem> {

    private String s1,s2;

    public MatchesAdapter(Context context, ArrayList<FasiFinaliContent.FasiFinaliItem> matches) {
        super(context, 0, matches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FasiFinaliContent.FasiFinaliItem match = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fasi_finali, parent, false);
        }
        // Lookup view for data population
        TextView matchScore = (TextView) convertView.findViewById(R.id.titleFasiFinali);
        TextView tavolo = (TextView) convertView.findViewById(R.id.textViewTavolo);
        TextView info = (TextView) convertView.findViewById(R.id.matchInfo);
        // Populate the data into the template view using the data object
        s1 = match.sq1;
        s2 = match.sq2;
        if(s1.length() > 18) {
            s1 = s1.substring(0, 18);
            s1 = s1+"...";
        }
        if(s2.length() > 18) {
            s2 = s2.substring(0, 18);
            s2 = s2+"...";
        }

        if(match.goal1==0 && match.goal2==0) {
            matchScore.setText(s1+" - "+s2);
        } else {
            matchScore.setText(s1+" - "+s2+"  "+match.goal1+"-"+match.goal2);
            tavolo.setVisibility(View.GONE);
            info.setVisibility(View.GONE);
        }

        tavolo.setText("TAVOLO "+match.tavolo);
        // Return the completed view to render on screen
        return convertView;
    }
}
