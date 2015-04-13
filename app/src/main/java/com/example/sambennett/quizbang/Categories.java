package com.example.sambennett.quizbang;

import android.app.Activity;

/**
 * Created by Jason on 4/4/2015.
 */
public class Categories {


    private int category;
    private String category_name;
    Activity activity;
    public Categories(String category_name, int category){
        this.category_name = category_name;
        this.category = category;
    }
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }



}
