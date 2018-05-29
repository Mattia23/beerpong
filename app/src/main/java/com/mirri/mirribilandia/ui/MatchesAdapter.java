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
        TextView matchScore = (TextView) convertView.findViewById(R.id.matchScore);
        // Populate the data into the template view using the data object
        matchScore.setText(match.sq1+" - "+match.sq2);
        // Return the completed view to render on screen
        return convertView;
    }
}
