package com.example.sambennett.quizbang;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private QustionDialog qDialog;
    private Vector<Integer> selected_categories;
    private SqlServerConnection gameDB;
    private TextView catOneTx, catTwoTx, catThreeTx, catFourTx, p_oneScoreTx, p_twoScoreTx, pOne_nameTx, pTwo_nameTx;
    private Random generator;
    private Questions question;
    Players player;

    private ArrayList<Questions> qOne, qTwo, qThree, qFour;

    private int[] buttonID = new int[]{
         /*   R.id.row1red,R.id.row1blue,R.id.row1purple,R.id.row1black,
            R.id.row2red,R.id.row2blue,R.id.row2purple,R.id.row2black,
            R.id.row3red,R.id.row3blue,R.id.row3purple,R.id.row3black,
            R.id.row4red,R.id.row4blue,R.id.row4purple,R.id.row4black*/

            R.id.row1red,R.id.row2red,R.id.row3red,R.id.row4red,
            R.id.row1blue,R.id.row2blue,R.id.row3blue,R.id.row4blue,
            R.id.row1purple,R.id.row2purple,R.id.row3purple,R.id.row4purple,
            R.id.row1black,R.id.row2black,R.id.row3black,R.id.row4black
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        pOne_nameTx = (TextView)findViewById(R.id.player_one_name);
        pTwo_nameTx = (TextView)findViewById(R.id.player_two_name);
        p_oneScoreTx =(TextView)findViewById(R.id.player_one_score);
        p_twoScoreTx =(TextView)findViewById(R.id.player_two_score);
        gameDB = new SqlServerConnection(this);
        qOne = new ArrayList<Questions>();
        qTwo = new ArrayList<Questions>();
        qThree = new ArrayList<Questions>();
        qFour = new ArrayList<Questions>();
        qDialog = new QustionDialog(this);

        catOneTx = (TextView)findViewById(R.id.cat_one);
        catTwoTx = (TextView)findViewById(R.id.cat_two);
        catThreeTx = (TextView)findViewById(R.id.cat_three);
        catFourTx = (TextView)findViewById(R.id.cat_four);

        mediaPlayer = MediaPlayer.create(this, R.raw.polka);
        tiles = new Vector<>();
        selected_categories = new Vector<>();

        for(int i = 0; i < buttonID.length; i = i+4){
            tiles.add(new GameTile(buttonID[i], this, R.drawable.redbutton));
            tiles.add(new GameTile(buttonID[i+1], this, R.drawable.bluebutton));
            tiles.add(new GameTile(buttonID[i+2], this, R.drawable.purplebutton));
            tiles.add(new GameTile(buttonID[i+3], this, R.drawable.blackbutton));
        }
        setSelectedCategories();
        start = (Button) findViewById(R.id.start);
        buttonState = true;

        start.setOnClickListener(this);


        Intent intent = getIntent(); // get passed intent
        player = (Players) intent.getSerializableExtra("player"); // get person object from intent
        pOne_nameTx.setText(player.getName());
        pTwo_nameTx.setText(player.getOppenents_name());
        p_oneScoreTx.setText("0");
        p_twoScoreTx.setText("0");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

        Log.i("CATEGORY", qOne.get(0).getCategory_name());

        if(buttonState){
            mediaPlayer.start();
            for(int i = 0; i < tiles.size(); i++){
                tiles.elementAt(i).setOff();
            }
            buttonState = false;
            start.setText("Stop");
            updateTask = new UpdateTask();
            updateTask.execute(1);
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
            qDialog.getQuestionDialog(setQuestion(active),player, tiles.elementAt(active).getPoints());




            Log.i("Active Index", Integer.toString(active));
            Log.i("POINT VALUE",""+ tiles.elementAt(active).getPoints());
            buttonState = true;
            start.setText("Start");
            if(updateTask != null){
                updateTask.cancel(true);
                updateTask = null;
            }
        }
    }

    class UpdateTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

           if(params[0]==1) {
                try {
                    while (true) {
                        generator = new Random();
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
                    player.setScore(0);
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
    public Questions setQuestion(int active){
        if(active <4){
            question = qOne.get(0);
            qOne.remove(0);
        }else if(active<8){
            question = qTwo.get(0);
            qTwo.remove(0);
        }else if(active<12){
            question = qThree.get(0);
            qThree.remove(0);
        }else{
            question = qFour.get(0);
            qFour.remove(0);
        }
        return question;
    }
    public void setSelectedCategories(){

        int size = gameDB.getCategorySize();
        for(int i =1; i <= size; i++){
            selected_categories.add(i);
        }
        Collections.shuffle(selected_categories);
        try {
            gameDB.selectQuestions(selected_categories.get(0).toString(), qOne);
            gameDB.selectQuestions(selected_categories.get(1).toString(), qTwo);
            gameDB.selectQuestions(selected_categories.get(2).toString(), qThree);
            gameDB.selectQuestions(selected_categories.get(3).toString(), qFour);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            catOneTx.setText(qOne.get(0).getCategory_name());
            catTwoTx.setText(qTwo.get(0).getCategory_name());
            catThreeTx.setText(qThree.get(0).getCategory_name());
            catFourTx.setText(qFour.get(0).getCategory_name());
        }
    }

}
