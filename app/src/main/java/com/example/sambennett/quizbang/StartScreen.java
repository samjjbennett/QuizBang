package com.example.sambennett.quizbang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
/**
 * Created by Jason on 4/13/2015.
 */
public class StartScreen extends Activity{

    EditText name, email, playername;
    Players player;
    int score = 0;
    int opponent_score =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        playername = (EditText) findViewById(R.id.playername);
        Button button = (Button) findViewById(R.id.signup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = new Players( name.getText().toString(),email.getText().toString(),playername.getText().toString());
                Log.i("PLAYER NAME", name.getText().toString());

                //http://hmkcode.com/android-passing-java-object-another-activity/
               Intent i = new Intent(StartScreen.this, MainActivity.class);
              // Intent i = new Intent("com.example.sambennett.quizbang.MAIN_ACTIVITY");
                i.putExtra("player", player);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
