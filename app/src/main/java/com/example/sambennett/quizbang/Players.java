package com.example.sambennett.quizbang;

import android.app.Activity;

import java.io.Serializable;

/**
 * Created by Jason on 4/13/2015.
 */
public  class Players implements Serializable {
    public String name;
    private String oppenents_name;
    private String playername;
    private String email;
    private int score;
    private int opponent_score;
    Activity activity;




    public Players( String name, String email, String playername){
        this.name = name;
        this.email = email;
        this.playername = playername;
        this.oppenents_name = "Charlie";
        this.score= 0;
        this.opponent_score = 0;
    }

    public Players(Activity activity){
        this.activity =  activity;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPlayername() {
        return playername;
    }
    public void setPlayername(String username) {
        this.playername = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOppenents_name() {
        return oppenents_name;
    }
    public void setOppenents_name(String oppenents_name) {
        this.oppenents_name = oppenents_name;
    }
    public int getOpponent_score() {
        return opponent_score;
    }
    public void setOpponent_score(int opponent_score) {
        this.opponent_score = opponent_score;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
