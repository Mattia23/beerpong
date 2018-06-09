package com.mirri.mirribilandia.ui;

import com.mirri.mirribilandia.ui.gironi.Team;

import java.util.Comparator;

/**
 * Created by Mattia on 27/05/2018.
 */
public class TerzeComparator implements Comparator<Team> {

    public TerzeComparator() {
    }

    @Override
    public int compare(Team t1, Team t2) {
        if(t1.getPoints() > t2.getPoints()) {
            return -1;
        } else if(t1.getPoints() < t2.getPoints()) {
            return 1;
        }

        if(t1.getGoalDone() > t2.getGoalDone()) {
            return -1;
        } else if(t1.getGoalDone() < t2.getGoalDone()) {
            return 1;
        }

        if(t1.getGoalDiff() > t2.getGoalDiff()) {
            return -1;
        } else if(t1.getGoalDiff() < t2.getGoalDiff()) {
            return 1;
        }

        return 0;
    }
}