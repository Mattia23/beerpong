package com.mirri.mirribilandia.ui.gironi;

import com.mirri.mirribilandia.item.GironiContent;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Mattia on 27/05/2018.
 */
public class TeamComparator implements Comparator<Team> {

    private List<GironiContent.PartiteItem> partite;

    public TeamComparator(List<GironiContent.PartiteItem> p) {
        this.partite = p;
    }

    @Override
    public int compare(Team t1, Team t2) {
        if(t1.getPoints() > t2.getPoints()) {
            return -1;
        } else if(t1.getPoints() < t2.getPoints()) {
            return 1;
        }

        for(GironiContent.PartiteItem p : partite) {
            if(p.sq1.equals(t1.getName()) && p.sq2.equals(t2.getName())) {
                if(p.goal1 > p.goal2) {
                    return -1;
                } else if (p.goal1 < p.goal2) {
                    return 1;
                }
            } else if (p.sq2.equals(t1.getName()) && p.sq1.equals(t2.getName())) {
                if(p.goal1 > p.goal2) {
                    return 1;
                } else if (p.goal1 < p.goal2) {
                    return -1;
                }
            }
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