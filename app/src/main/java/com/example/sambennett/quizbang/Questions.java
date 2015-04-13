package com.example.sambennett.quizbang;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by Jason on 4/4/2015.
 */
public class Questions extends Categories {

   // private int category;
   // private String category_name;
    private String question;
    private String choice_one;
    private String choice_two;
    private String choice_three;
    private String choice_four;
    Activity activity;
    TextView tv;
    public Questions(int category, String category_name, String question, String choice_one, String choice_two, String choice_three, String choice_four) {
        super(category_name, category);
        category = category;
        category_name = category_name;
        this.question = question;
        this.choice_one = choice_one;
        this.choice_two = choice_two;
        this.choice_three = choice_three;
        this.choice_four = choice_four;
    }


    public Questions(String category_name, int category) {
        super(category_name, category);
    }
    public void setQuestion(String question){
        this.question = question;
   }
    public String getQuestion(){
        return  question;
    }
    public void setCorrectAnswer(String choice_one){
        this.choice_one = choice_one;
    }
    public String getCorrectAnswer(){
        return choice_one;
    }
    public void setChoice_two(String choice_two){
        this.choice_two = choice_two;
    }
    public String getChoice_two(){
        return choice_two;
    }
    public void setChoice_three(String choice_three){
        this.choice_three = choice_three;
    }
    public String getChoice_three(){
        return choice_three;
    }
    public void setChoice_four(String choice_four){
        this.choice_four = choice_four;
    }
    public String getChoice_four(){
        return choice_four;
    }


    public String toString() {
        String  results = getCategory()+" \n";
        results += getCategory_name()+ " \n";
        results += question + " \n";
        results += choice_one + " \n";
        results += choice_two + " \n";
        results += choice_three + " \n";
        results += choice_four;

        return results;
    }

}
