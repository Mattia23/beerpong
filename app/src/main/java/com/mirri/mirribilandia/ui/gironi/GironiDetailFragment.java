package com.mirri.mirribilandia.ui.gironi;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GironiDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";
    private GironiContent.PartiteItem partiteItem;
    private List<GironiContent.PartiteItem> partite = new ArrayList<>();
    private List<Team> teams;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            partiteItem = GironiContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
        computeTable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(Color.parseColor(("#1976d2")));
        }
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_gironi_detail);
        editResults(rootView);
        editTable(rootView);

        CollapsingToolbarLayout name = rootView.findViewById(R.id.collapsing_toolbar);
        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }
        if (partiteItem != null) {
            name.setTitle("Girone "+partiteItem.girone);
        }
        return rootView;
    }

    private void editResults(View rootView) {
        TextView squadra1 = (TextView) rootView.findViewById(R.id.squadra1);
        squadra1.setText(partite.get(0).sq1);
        TextView squadra2 = (TextView) rootView.findViewById(R.id.squadra2);
        squadra2.setText(partite.get(0).sq2);
        TextView squadra3 = (TextView) rootView.findViewById(R.id.squadra3);
        squadra3.setText(partite.get(1).sq1);
        TextView squadra4 = (TextView) rootView.findViewById(R.id.squadra4);
        squadra4.setText(partite.get(1).sq2);
        TextView squadra5 = (TextView) rootView.findViewById(R.id.squadra5);
        squadra5.setText(partite.get(2).sq1);
        TextView squadra6 = (TextView) rootView.findViewById(R.id.squadra6);
        squadra6.setText(partite.get(2).sq2);
        TextView squadra7 = (TextView) rootView.findViewById(R.id.squadra7);
        squadra7.setText(partite.get(3).sq1);
        TextView squadra8 = (TextView) rootView.findViewById(R.id.squadra8);
        squadra8.setText(partite.get(3).sq2);
        TextView squadra9 = (TextView) rootView.findViewById(R.id.squadra9);
        squadra9.setText(partite.get(4).sq1);
        TextView squadra10 = (TextView) rootView.findViewById(R.id.squadra10);
        squadra10.setText(partite.get(4).sq2);
        TextView squadra11 = (TextView) rootView.findViewById(R.id.squadra11);
        squadra11.setText(partite.get(5).sq1);
        TextView squadra12 = (TextView) rootView.findViewById(R.id.squadra12);
        squadra12.setText(partite.get(5).sq2);

        TextView goal1 = (TextView) rootView.findViewById(R.id.goal1);
        goal1.setText("" + partite.get(0).goal1);
        TextView goal2 = (TextView) rootView.findViewById(R.id.goal2);
        goal2.setText("" + partite.get(0).goal2);
        TextView goal3 = (TextView) rootView.findViewById(R.id.goal3);
        goal3.setText("" + partite.get(1).goal1);
        TextView goal4 = (TextView) rootView.findViewById(R.id.goal4);
        goal4.setText("" + partite.get(1).goal2);
        TextView goal5 = (TextView) rootView.findViewById(R.id.goal5);
        goal5.setText("" + partite.get(2).goal1);
        TextView goal6 = (TextView) rootView.findViewById(R.id.goal6);
        goal6.setText("" + partite.get(2).goal2);
        TextView goal7 = (TextView) rootView.findViewById(R.id.goal7);
        goal7.setText("" + partite.get(3).goal1);
        TextView goal8 = (TextView) rootView.findViewById(R.id.goal8);
        goal8.setText("" + partite.get(3).goal2);
        TextView goal9 = (TextView) rootView.findViewById(R.id.goal9);
        goal9.setText("" + partite.get(4).goal1);
        TextView goal10 = (TextView) rootView.findViewById(R.id.goal10);
        goal10.setText("" + partite.get(4).goal2);
        TextView goal11 = (TextView) rootView.findViewById(R.id.goal11);
        goal11.setText("" + partite.get(5).goal1);
        TextView goal12 = (TextView) rootView.findViewById(R.id.goal12);
        goal12.setText("" + partite.get(5).goal2);
    }

    private void editTable(View rootView) {
        Collections.sort(teams, new TeamComparator(partite));

        TextView squadra1 = (TextView) rootView.findViewById(R.id.sq1);
        TextView squadra2 = (TextView) rootView.findViewById(R.id.sq2);
        TextView squadra3 = (TextView) rootView.findViewById(R.id.sq3);
        TextView squadra4 = (TextView) rootView.findViewById(R.id.sq4);
        squadra1.setText(teams.get(0).getName());
        squadra2.setText(teams.get(1).getName());
        squadra3.setText(teams.get(2).getName());
        squadra4.setText(teams.get(3).getName());
        TextView win1 = (TextView) rootView.findViewById(R.id.winSq1);
        TextView win2 = (TextView) rootView.findViewById(R.id.winSq2);
        TextView win3 = (TextView) rootView.findViewById(R.id.winSq3);
        TextView win4 = (TextView) rootView.findViewById(R.id.winSq4);
        win1.setText(""+teams.get(0).getWins());
        win2.setText(""+teams.get(1).getWins());
        win3.setText(""+teams.get(2).getWins());
        win4.setText(""+teams.get(3).getWins());
        TextView draw1 = (TextView) rootView.findViewById(R.id.drawSq1);
        TextView draw2 = (TextView) rootView.findViewById(R.id.drawSq2);
        TextView draw3 = (TextView) rootView.findViewById(R.id.drawSq3);
        TextView draw4 = (TextView) rootView.findViewById(R.id.drawSq4);
        draw1.setText(""+teams.get(0).getDraws());
        draw2.setText(""+teams.get(1).getDraws());
        draw3.setText(""+teams.get(2).getDraws());
        draw4.setText(""+teams.get(3).getDraws());
        TextView lose1 = (TextView) rootView.findViewById(R.id.loseSq1);
        TextView lose2 = (TextView) rootView.findViewById(R.id.loseSq2);
        TextView lose3 = (TextView) rootView.findViewById(R.id.loseSq3);
        TextView lose4 = (TextView) rootView.findViewById(R.id.loseSq4);
        lose1.setText(""+teams.get(0).getLoses());
        lose2.setText(""+teams.get(1).getLoses());
        lose3.setText(""+teams.get(2).getLoses());
        lose4.setText(""+teams.get(3).getLoses());
        TextView goal1 = (TextView) rootView.findViewById(R.id.goalSq1);
        TextView goal2 = (TextView) rootView.findViewById(R.id.goalSq2);
        TextView goal3 = (TextView) rootView.findViewById(R.id.goalSq3);
        TextView goal4 = (TextView) rootView.findViewById(R.id.goalSq4);
        goal1.setText(""+teams.get(0).getGoalDone());
        goal2.setText(""+teams.get(1).getGoalDone());
        goal3.setText(""+teams.get(2).getGoalDone());
        goal4.setText(""+teams.get(3).getGoalDone());
        TextView dr1 = (TextView) rootView.findViewById(R.id.drSq1);
        TextView dr2 = (TextView) rootView.findViewById(R.id.drSq2);
        TextView dr3 = (TextView) rootView.findViewById(R.id.drSq3);
        TextView dr4 = (TextView) rootView.findViewById(R.id.drSq4);
        dr1.setText(""+teams.get(0).getGoalDiff());
        dr2.setText(""+teams.get(1).getGoalDiff());
        dr3.setText(""+teams.get(2).getGoalDiff());
        dr4.setText(""+teams.get(3).getGoalDiff());
        TextView points1 = (TextView) rootView.findViewById(R.id.puntiSq1);
        TextView points2 = (TextView) rootView.findViewById(R.id.puntiSq2);
        TextView points3 = (TextView) rootView.findViewById(R.id.puntiSq3);
        TextView points4 = (TextView) rootView.findViewById(R.id.puntiSq4);
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
            if(p.goal1 != 0 && p.goal2 != 0) {
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
}
