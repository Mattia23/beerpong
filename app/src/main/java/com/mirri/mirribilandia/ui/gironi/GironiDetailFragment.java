package com.mirri.mirribilandia.ui.gironi;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.item.SquadreContent;
import com.mirri.mirribilandia.ui.FasiFinaliActivity;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;
import com.mirri.mirribilandia.util.Counter;
import com.mirri.mirribilandia.util.MyCustomProgressDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GironiDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";
    private List<GironiContent.PartiteItem> partite = new ArrayList<>();
    private List<Team> teams;
    private Boolean wasOnPause = false;
    protected ProgressDialog progressDialog = null;
    private TextView sq1, sq2, sq3, sq4, win1, win2, win3, win4, draw1, draw2, draw3, draw4, lose1, lose2, lose3,
                     lose4, goal1, goal2, goal3, goal4, dr1, dr2, dr3, dr4, points1, points2, points3, points4;
    private TextView squadra1, squadra2, squadra3, squadra4, squadra5, squadra6, squadra7, squadra8, squadra9, squadra10,
                     squadra11, squadra12, score1, score2, score3, score4, score5, score6, score7, score8, score9, score10,
                     score11, score12;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(Color.parseColor(("#1976d2")));
        }
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_gironi_detail);
        setHasOptionsMenu(true);

        createGraphicResults(rootView);
        createGraphicTable(rootView);

        progressDialog = MyCustomProgressDialog.ctor(getActivity());

        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }
        return rootView;
    }

    private void createGraphicResults(View rootView) {
        squadra1 = (TextView) rootView.findViewById(R.id.squadra1);
        squadra2 = (TextView) rootView.findViewById(R.id.squadra2);
        squadra3 = (TextView) rootView.findViewById(R.id.squadra3);
        squadra4 = (TextView) rootView.findViewById(R.id.squadra4);
        squadra5 = (TextView) rootView.findViewById(R.id.squadra5);
        squadra6 = (TextView) rootView.findViewById(R.id.squadra6);
        squadra7 = (TextView) rootView.findViewById(R.id.squadra7);
        squadra8 = (TextView) rootView.findViewById(R.id.squadra8);
        squadra9 = (TextView) rootView.findViewById(R.id.squadra9);
        squadra10 = (TextView) rootView.findViewById(R.id.squadra10);
        squadra11 = (TextView) rootView.findViewById(R.id.squadra11);
        squadra12 = (TextView) rootView.findViewById(R.id.squadra12);

        score1 = (TextView) rootView.findViewById(R.id.goal1);
        score2 = (TextView) rootView.findViewById(R.id.goal2);
        score3 = (TextView) rootView.findViewById(R.id.goal3);
        score4 = (TextView) rootView.findViewById(R.id.goal4);
        score5 = (TextView) rootView.findViewById(R.id.goal5);
        score6 = (TextView) rootView.findViewById(R.id.goal6);
        score7 = (TextView) rootView.findViewById(R.id.goal7);
        score8 = (TextView) rootView.findViewById(R.id.goal8);
        score9 = (TextView) rootView.findViewById(R.id.goal9);
        score10 = (TextView) rootView.findViewById(R.id.goal10);
        score11 = (TextView) rootView.findViewById(R.id.goal11);
        score12 = (TextView) rootView.findViewById(R.id.goal12);
    }

    private void editResults() {
        squadra1.setText(partite.get(0).sq1);
        squadra2.setText(partite.get(0).sq2);
        squadra3.setText(partite.get(1).sq1);
        squadra4.setText(partite.get(1).sq2);
        squadra5.setText(partite.get(2).sq1);
        squadra6.setText(partite.get(2).sq2);
        squadra7.setText(partite.get(3).sq1);
        squadra8.setText(partite.get(3).sq2);
        squadra9.setText(partite.get(4).sq1);
        squadra10.setText(partite.get(4).sq2);
        squadra11.setText(partite.get(5).sq1);
        squadra12.setText(partite.get(5).sq2);

        score1.setText("" + partite.get(0).goal1);
        score2.setText("" + partite.get(0).goal2);
        score3.setText("" + partite.get(1).goal1);
        score4.setText("" + partite.get(1).goal2);
        score5.setText("" + partite.get(2).goal1);
        score6.setText("" + partite.get(2).goal2);
        score7.setText("" + partite.get(3).goal1);
        score8.setText("" + partite.get(3).goal2);
        score9.setText("" + partite.get(4).goal1);
        score10.setText("" + partite.get(4).goal2);
        score11.setText("" + partite.get(5).goal1);
        score12.setText("" + partite.get(5).goal2);
    }

    private void createGraphicTable(View rootView) {
        sq1 = (TextView) rootView.findViewById(R.id.sq1);
        sq2 = (TextView) rootView.findViewById(R.id.sq2);
        sq3 = (TextView) rootView.findViewById(R.id.sq3);
        sq4 = (TextView) rootView.findViewById(R.id.sq4);
        win1 = (TextView) rootView.findViewById(R.id.winSq1);
        win2 = (TextView) rootView.findViewById(R.id.winSq2);
        win3 = (TextView) rootView.findViewById(R.id.winSq3);
        win4 = (TextView) rootView.findViewById(R.id.winSq4);
        draw1 = (TextView) rootView.findViewById(R.id.drawSq1);
        draw2 = (TextView) rootView.findViewById(R.id.drawSq2);
        draw3 = (TextView) rootView.findViewById(R.id.drawSq3);
        draw4 = (TextView) rootView.findViewById(R.id.drawSq4);
        lose1 = (TextView) rootView.findViewById(R.id.loseSq1);
        lose2 = (TextView) rootView.findViewById(R.id.loseSq2);
        lose3 = (TextView) rootView.findViewById(R.id.loseSq3);
        lose4 = (TextView) rootView.findViewById(R.id.loseSq4);
        goal1 = (TextView) rootView.findViewById(R.id.goalSq1);
        goal2 = (TextView) rootView.findViewById(R.id.goalSq2);
        goal3 = (TextView) rootView.findViewById(R.id.goalSq3);
        goal4 = (TextView) rootView.findViewById(R.id.goalSq4);
        dr1 = (TextView) rootView.findViewById(R.id.drSq1);
        dr2 = (TextView) rootView.findViewById(R.id.drSq2);
        dr3 = (TextView) rootView.findViewById(R.id.drSq3);
        dr4 = (TextView) rootView.findViewById(R.id.drSq4);
        points1 = (TextView) rootView.findViewById(R.id.puntiSq1);
        points2 = (TextView) rootView.findViewById(R.id.puntiSq2);
        points3 = (TextView) rootView.findViewById(R.id.puntiSq3);
        points4 = (TextView) rootView.findViewById(R.id.puntiSq4);
    }

    private void editTable() {
        Collections.sort(teams, new TeamComparator(partite));
        sq1.setText(teams.get(0).getName());
        sq2.setText(teams.get(1).getName());
        sq3.setText(teams.get(2).getName());
        sq4.setText(teams.get(3).getName());
        win1.setText(""+teams.get(0).getWins());
        win2.setText(""+teams.get(1).getWins());
        win3.setText(""+teams.get(2).getWins());
        win4.setText(""+teams.get(3).getWins());
        draw1.setText(""+teams.get(0).getDraws());
        draw2.setText(""+teams.get(1).getDraws());
        draw3.setText(""+teams.get(2).getDraws());
        draw4.setText(""+teams.get(3).getDraws());
        lose1.setText(""+teams.get(0).getLoses());
        lose2.setText(""+teams.get(1).getLoses());
        lose3.setText(""+teams.get(2).getLoses());
        lose4.setText(""+teams.get(3).getLoses());
        goal1.setText(""+teams.get(0).getGoalDone());
        goal2.setText(""+teams.get(1).getGoalDone());
        goal3.setText(""+teams.get(2).getGoalDone());
        goal4.setText(""+teams.get(3).getGoalDone());
        dr1.setText(""+teams.get(0).getGoalDiff());
        dr2.setText(""+teams.get(1).getGoalDiff());
        dr3.setText(""+teams.get(2).getGoalDiff());
        dr4.setText(""+teams.get(3).getGoalDiff());
        points1.setText(""+teams.get(0).getPoints());
        points2.setText(""+teams.get(1).getPoints());
        points3.setText(""+teams.get(2).getPoints());
        points4.setText(""+teams.get(3).getPoints());
    }

    private void computeTable() {

        for (GironiContent.PartiteItem p : GironiContent.ITEMS) {
            if(p.girone == Integer.parseInt(getArguments().getString(ARG_ITEM_ID))) {
                partite.add(p);
            }
            if(partite.size() == 6) {
                break;
            }
        }

        teams = new ArrayList<>(Arrays.asList(new Team(partite.get(0).sq1),
                new Team(partite.get(0).sq2),
                new Team(partite.get(1).sq1),
                new Team(partite.get(1).sq2)));

        Team s1 = null, s2 = null;
        for(GironiContent.PartiteItem p : partite) {
            if(!(p.goal1 == 0 && p.goal2 == 0)) {
                for (Team t : teams) {
                    if (t.getName().equals(p.sq1)) {
                        s1 = t;
                    } else if (t.getName().equals(p.sq2)) {
                        s2 = t;
                    }
                }

                s1.setGoalDone(p.goal1);
                s1.setGoalSubmit(p.goal2);
                s2.setGoalDone(p.goal2);
                s2.setGoalSubmit(p.goal1);

                if (p.goal1 > p.goal2) {
                    s1.win();
                    s2.lose();
                    s1.setPoints(3);
                } else if (p.goal1 < p.goal2) {
                    s2.win();
                    s1.lose();
                    s2.setPoints(3);
                } else {
                    s2.draw();
                    s1.draw();
                    s1.setPoints(1);
                    s2.setPoints(1);
                }
            }
        }
    }

    public static GironiDetailFragment newInstance(String itemID) {
        GironiDetailFragment fragment = new GironiDetailFragment();
        Bundle args = new Bundle();
        args.putString(GironiDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public GironiDetailFragment() {}

    @Override
    public void onPause() {
        super.onPause();
        wasOnPause = true;
    }

    private void refreshContents() {
        progressDialog.show();
        Counter c = new Counter(null,null, this,null,null);
        new FasiFinaliContent(getActivity().getApplicationContext(), c);
        new GironiContent(getActivity().getApplicationContext(), c);
        new SquadreContent(getActivity().getApplicationContext(), c);
    }

    public void stopLoadingSpinner() {
        computeTable();
        editResults();
        editTable();
        progressDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        partite = new ArrayList<>();
        if(wasOnPause) {
            refreshContents();
            wasOnPause = false;
        } else {
            computeTable();
            editResults();
            editTable();
        }
    }
}
