package com.example.sambennett.quizbang;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by sambennett on 4/3/15.
 */
public class GameTile {
    private TextView tile;
    private int color;
    private Activity activity;
    private int points;
    private boolean onState;
    private boolean killState;

    public GameTile(int id, Activity activity, int color){
        tile = (TextView) activity.findViewById(id);
        this.color = color;
        this.activity = activity;
        onState = false;
        killState = false;
        points = 0;
    }

    public boolean isKillState() {
        return killState;
    }

    public void setOn(){
        onState = true;
        tile.setBackgroundResource(R.drawable.whitebutton);
    }

    public void setOff(){
        onState = false;
        tile.setBackgroundResource(color);
    }

    public void setKillOn(){
        killState = true;
        tile.setBackgroundResource(R.drawable.kill);
    }

    public void setKillOff(){
        killState = false;
        tile.setBackgroundResource(color);
    }

    public void setKillAndActive(){
        onState = false;
        killState = true;
        tile.setBackgroundDrawable(activity.getBaseContext().getResources().getDrawable(R.drawable.activekill));
    }

    public boolean isOnState() {
        return onState;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        tile.setText(Integer.toString(points));
    }
}
