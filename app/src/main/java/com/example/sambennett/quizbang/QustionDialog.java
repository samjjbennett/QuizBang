package com.example.sambennett.quizbang;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jason on 3/22/2015.
 */
public class QustionDialog {

    public Activity activity;
    public Dialog dialog;
    private TextView tx;
    private Button button;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    ArrayList<Integer> num;
    TextView cat_tv;
    boolean isCorrect;
    int player_score;
    Players player;

    Questions questions;

    public QustionDialog(Activity activity) {
        this.activity = activity;
        num = new ArrayList<Integer>();
        for (int i=0; i< 4; i++){
            num.add(i);
        }


    }
    public void setQuestion(Questions questions){
        this.questions = questions;

    }


    public void getQuestionDialog(final Questions questions,final Players player,final int tile_vlue) {
       this.player = player;
        final TextView scoreTx = (TextView)activity.findViewById(R.id.player_one_score);
      //  final Questions question = questions;
      //  final int tile_point_value = score;

        Collections.shuffle(num);
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cat_tv = (TextView)dialog.findViewById(R.id.category_tx);
        cat_tv.setText(questions.getCategory_name());
        tx = (TextView)dialog.findViewById(R.id.question_tx);
        tx.setText(""+questions.getQuestion());
        button = (Button) dialog.findViewById(R.id.submit_answer);

        radioGroup = (RadioGroup)dialog.findViewById(R.id.q_radio);
        ((RadioButton) radioGroup.getChildAt(num.get(0))).setText(String.valueOf(questions.getCorrectAnswer()));
        ((RadioButton) radioGroup.getChildAt(num.get(1))).setText(String.valueOf(questions.getChoice_two()));
        ((RadioButton) radioGroup.getChildAt(num.get(2))).setText(String.valueOf(questions.getChoice_three()));
        ((RadioButton) radioGroup.getChildAt(num.get(3))).setText(String.valueOf(questions.getChoice_four()));

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int selectedId = -1;
                selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) dialog.findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(activity, "Please Make a selection", Toast.LENGTH_SHORT).show();
                } else {
                    if (questions.getCorrectAnswer().equals(radioButton.getText())) {
                        Toast.makeText(activity, "Correct", Toast.LENGTH_LONG).show();
                        isCorrect = true;
                        player_score += tile_vlue;
                        scoreTx.setText("Score: "+ player_score);
                    } else {
                        Toast.makeText(activity, "Wrong Answer Correct answer is " + questions.getCorrectAnswer(), Toast.LENGTH_LONG).show();
                        isCorrect = false;
                        scoreTx.setText("Score: "+ player_score);
                    }
                    dialog.dismiss();
                }
            }
        });
            dialog.show();
        }
}
