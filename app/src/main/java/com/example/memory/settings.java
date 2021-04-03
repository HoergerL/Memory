package com.example.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class settings extends AppCompatActivity {
    public RadioButton easy, intermediate, difficult;
    public RadioButton fruits, transport, animals;
    public RadioButton singleplayer, multiplayer;
    private static int flag_difficulty = 2;
    private static int flag_topic = 1;
    private static int flag_player = 1;

    private SharedPreferences settings;



    public void settingsHandlerMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        easy = (RadioButton) findViewById(R.id.easy);
        intermediate = (RadioButton) findViewById(R.id.intermediate);
        difficult = (RadioButton) findViewById(R.id.difficult);

        fruits = (RadioButton) findViewById(R.id.fruits);
        animals = (RadioButton) findViewById(R.id.animals);
        transport = (RadioButton) findViewById(R.id.transport);

        singleplayer = (RadioButton) findViewById(R.id.singleplayer);
        multiplayer = (RadioButton) findViewById(R.id.multiplayer);

      // settings = getSharedPreferences("settings", MODE_PRIVATE);

        /*flag_player = settings.getInt("flag_player", flag_player);
        flag_difficulty = settings.getInt("flag_difficulty",flag_difficulty);
        flag_topic = settings.getInt("flag_topic", flag_topic);
        System.out.println("FLAGS: " + flag_difficulty +", " + flag_topic+", "+ flag_player  +"#################################"); */

        if(flag_player==2){
            System.out.println("Multiplayer selected");
            multiplayer.setChecked(true);
        }
        else if (flag_player==1){
            singleplayer.setChecked(true);

        }

        if(flag_difficulty==1){
            easy.setChecked(true);
        }
        else if(flag_difficulty==3){
            difficult.setChecked(true);
        }
        else if(flag_difficulty==2){
            intermediate.setChecked(true);
        }

        if(flag_topic==1){
            fruits.setChecked(true);
        }
        else if (flag_topic==2){
            animals.setChecked(true);
        }
        else if(flag_topic==3){
            transport.setChecked(true);
        }


        singleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_player = 1;
                singleplayer.setChecked(true);
                multiplayer.setChecked(false);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_player = 2;
                multiplayer.setChecked(true);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_topic = 1;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_topic = 2;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_topic = 3;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_difficulty = 1;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_difficulty = 2;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_difficulty = 3;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
     /* settings.edit().putInt("flag_player",flag_player).apply();
        settings.edit().putInt("flag_topic",flag_topic).apply();
        settings.edit().putInt("flag_difficulty",flag_difficulty).apply();

        settings.edit().commit(); */
    }
    public static int getFlag_difficulty(){
        return flag_difficulty;
    }

    public static int getFlag_topic(){
        return flag_topic;
    }
    public static int getFlag_player(){
        return flag_player;
    }
}
