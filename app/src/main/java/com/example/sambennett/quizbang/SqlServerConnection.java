package com.example.sambennett.quizbang;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jason on 4/12/2015.
 */
public class SqlServerConnection {
    private Connection con = null;
    private ResultSet rs= null;
    private PreparedStatement stmt = null;
    private Activity activity;
    private int category_size;
    final String SELECT_QUESTIONS = "select top 50 * from questions where category = ?";
    final String SELECT_CATEGORY_SIZE = "SELECT COUNT(DISTINCT category) as size FROM questions";
    final String HOST ="jdbc:jtds:sqlserver://184.168.194.53;databaseName=ms_quizbang;integratedSecurity=true";
    final String USERNAME = "jason";
    final String PASSWORD = "Ysabel2007";
    private Questions question;

    public SqlServerConnection(Activity activity){
        this.activity = activity;
        con = null ;
        rs = null;
        stmt = null;
    }
    public void selectQuestions(String param, ArrayList<Questions> qlist ) throws SQLException {
        Log.i("GETTING QUESTIONS", "GETTING QUESTIONS");

        try{
            String id=null;
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            try {
                stmt = con.prepareStatement(SELECT_QUESTIONS);
                stmt.setString(1, param);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    id = rs.getString("id");
                    qlist.add(new Questions(rs.getInt("category"),
                            rs.getString("category_name"),
                            rs.getString("question"),
                            rs.getString("choice_one"),
                            rs.getString("choice_two"),
                            rs.getString("choice_three"),
                            rs.getString("choice_four")));
                    Log.i("RESULTS" + id + "," + param, rs.getString("category_name"));
                }
                if(rs.next()) {
                    //Start new activity
                    AlertDialog ad = new AlertDialog.Builder(activity).create();
                    ad.setTitle("Success!");
                    ad.setMessage("You have logged in successfully!");
                    ad.setButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    ad.show();
                }
                rs.close();
                stmt.close();
            }
            catch (Exception e) {
                stmt.close();
                AlertDialog ad = new AlertDialog.Builder(activity).create();
                ad.setTitle("Error");
                ad.setMessage("Prepared statement error: " + e.getMessage());
                ad.setButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                ad.show();
            }
            con.close();
        }
        catch (Exception e) {
            AlertDialog ad = new AlertDialog.Builder(activity).create();
            ad.setTitle("Error");
            ad.setMessage("Connection error: " + e.getMessage());
            ad.setButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            ad.show();
        }
    }
    public int getCategorySize() {
        Log.i("GETTING CATEGORY SIZE","GETTING CATEGORY SIZE" );
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

            stmt = con.prepareStatement(SELECT_CATEGORY_SIZE);
            rs = stmt.executeQuery();

            while (rs.next()) {
                category_size = rs.getInt("size");
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return category_size;
    }

    public Questions getQuestion(){


        return question;
    }
    public  void setQuestion(ArrayList<Questions> questions, int index){
        question = questions.get(index);
    }
}
