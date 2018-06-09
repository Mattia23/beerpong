package com.mirri.mirribilandia.ui;

import android.util.Log;

import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.ui.gironi.Team;
import com.mirri.mirribilandia.ui.gironi.TeamComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mattia on 09/06/2018.
 */

public class TerzeContainer {

    private List<GironiContent.PartiteItem> partite = new ArrayList<>();
    private List<Team> teams;
    private Map<Integer,Team> terziTeams = new HashMap<>();
    private List<Team> terziTeamsList;
    private TerzeComparator comparator = new TerzeComparator();

    public TerzeContainer () {
        refreshContainer();
    }

    public void refreshContainer() {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();

                for(int i=1; i<= GironiContent.GIRONI_ITEMS.size(); i++) {
                    partite.clear();
                    for (GironiContent.PartiteItem p : GironiContent.ITEMS) {
                        if(p.girone == i) {
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
                    Collections.sort(teams, new TeamComparator(partite));
                    terziTeams.put(i,teams.get(2));

                }
                terziTeamsList = new ArrayList<>(terziTeams.values());
                Collections.sort(terziTeamsList, comparator);

            }
        };
        t.start();
    }

    public List<Team> getThirdListTeams() {
        return terziTeamsList;
    }


}
