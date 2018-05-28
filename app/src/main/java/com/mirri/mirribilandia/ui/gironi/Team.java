package com.mirri.mirribilandia.ui.gironi;

/**
 * Created by Mattia on 26/05/2018.
 */

public class Team {

    private String name;
    private int wins = 0;
    private int draws = 0;
    private int loses = 0;
    private int goalDone = 0;
    private int goalSubmit = 0;
    private int points = 0;

    public Team (String nome) {
        this.name = nome;
    }

    public String getName() {
        return this.name;
    }

    public void win() {
        this.wins++;
    }

    public void draw() {
        this.draws++;
    }

    public void lose() {
        this.loses++;
    }

    public void setGoalDone(int gol) {
        this.goalDone += gol;
    }

    public void setGoalSubmit(int gol) {
        this.goalSubmit += gol;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    int getWins() {
        return this.wins;
    }

    int getDraws() {
        return this.draws;
    }

    int getLoses() {
        return this.loses;
    }

    int getGoalDone() {
        return this.goalDone;
    }

    int getGoalSubmit() {
        return this.goalSubmit;
    }

    int getGoalDiff() {
        return this.goalDone - this.goalSubmit;
    }

    int getPoints() {
        return this.points;
    }

}
