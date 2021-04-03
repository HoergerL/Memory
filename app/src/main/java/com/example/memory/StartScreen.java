package com.example.memory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    // TODO: set first-, second- and thirdPlace!
    static int firstPlace = 3;
    static int secondPlace = 2;
    static int thirdPlace = 1;

    static SharedPreferences preferences;
    static SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // so start screen gets called only once in the Main activity:
        MainActivity.startScreenShown = true;

        // get highscore
        preferences = getSharedPreferences("prefernceFile",  MODE_PRIVATE);
        prefsEditor = preferences.edit();
        // get saved highscore
        getHighscoreFromPreferences();
    }


    // sets the highscore in the StartScreen Acitivity from the values saved in the preferences!!
    public void getHighscoreFromPreferences() {
        firstPlace = preferences.getInt("first", 2);
        secondPlace = preferences.getInt("second", 1);
        thirdPlace = preferences.getInt("third", 0);
    }


    public static void saveHighscoreInPreferences() {
        prefsEditor.putInt("first", firstPlace);
        prefsEditor.putInt("second", secondPlace);
        prefsEditor.putInt("third", thirdPlace);
        prefsEditor.commit();
    }

    public void clearHighscore(View view) {
        firstPlace = 0;
        secondPlace = 0;
        thirdPlace = 0;
        saveHighscoreInPreferences();
        showHighScoreAlert();
    }



    public void showHighScore(View view) {
        showHighScoreAlert();
    }

    public void showHighScoreAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartScreen.this);
        String leaderBoardString = "Highscore\n 1. " +firstPlace+ "\n 2. " + secondPlace+ "\n 3. " + thirdPlace;
        alertDialogBuilder.setMessage(leaderBoardString).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // just restart the StartScreen activity
                Intent intent = new Intent(getApplicationContext(),StartScreen.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void startMainAcitivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startSettingsActivity(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }
}
