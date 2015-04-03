package com.example.sambennett.quizbang;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;

import java.util.Random;
import java.util.Vector;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private int[] buttonID = new int[]{
            R.id.row1red,R.id.row1blue,R.id.row1purple,R.id.row1black,
            R.id.row2red,R.id.row2blue,R.id.row2purple,R.id.row2black,
            R.id.row3red,R.id.row3blue,R.id.row3purple,R.id.row3black,
            R.id.row4red,R.id.row4blue,R.id.row4purple,R.id.row4black
    };

    private int[] pointValues = new int[]{100,200,300,400,500,600,700,800,900,1000};

    private UpdateTask updateTask;
    private MediaPlayer mediaPlayer;
    private Vector<GameTile> tiles;
    private Button start;
    private boolean buttonState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.polka);

        tiles = new Vector<>();

        for(int i = 0; i < buttonID.length; i = i+4){
            tiles.add(new GameTile(buttonID[i], this, R.drawable.redbutton));
            tiles.add(new GameTile(buttonID[i+1], this, R.drawable.bluebutton));
            tiles.add(new GameTile(buttonID[i+2], this, R.drawable.purplebutton));
            tiles.add(new GameTile(buttonID[i+3], this, R.drawable.blackbutton));
        }

        start = (Button) findViewById(R.id.start);
        buttonState = true;

        start.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(buttonState){
            mediaPlayer.start();
            for(int i = 0; i < tiles.size(); i++){
                tiles.elementAt(i).setOff();
            }
            buttonState = false;
            start.setText("Stop");
            updateTask = new UpdateTask();
            updateTask.execute();
        }else{
               mediaPlayer.pause();
            int active = 0;
            //The for loop finds the element that is active.
            for(int i = 0; i < tiles.size(); i++){
                if(tiles.elementAt(i).isOnState()){
                    active = i;
                    break;
                }
            }
            Log.i("Active Index", Integer.toString(active));
            buttonState = true;
            start.setText("Start");
            if(updateTask != null){
                updateTask.cancel(true);
                updateTask = null;
            }

        }
    }

    class UpdateTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                while(true) {
                    Random generator = new Random();
                    int id = generator.nextInt(buttonID.length) + 0;
                    int killId = generator.nextInt(buttonID.length) + 0;
                    if (!isCancelled()) {

                        publishProgress(1, id, killId);
                        Thread.sleep(100);
                        publishProgress(-1, id, killId);
                    }
                }

            } catch (InterruptedException e) {
                Log.i("VALUES", "----- INTERRUPTED -----");
            } finally {
                updateTask = null;
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(values[0] == 1){
                //Creates random point valuse for tiles
                Random generator = new Random();
                for(int i  = 0; i < tiles.size(); i++){
                    int points = generator.nextInt(9) + 0;
                    if(values[2] != i){
                        tiles.elementAt(i).setPoints(pointValues[points]);
                    }else{
                        tiles.elementAt(i).clearPoints();
                    }
                }
                //Sets buttons state to on
                if(values[1] == values[2]){
                    tiles.elementAt(values[1]).setKillAndActive();
                }else{
                    tiles.elementAt(values[1]).setOn();
                    tiles.elementAt(values[2]).setKillOn();
                }


            }else{
                //Sets button state to off
                tiles.elementAt(values[1]).setOff();
                tiles.elementAt(values[2]).setKillOff();
            }
        }

    }
}
