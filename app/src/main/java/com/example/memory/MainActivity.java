package com.example.memory;

//import android.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;



public class MainActivity extends AppCompatActivity {

    // Event handler for Settings-Button click
    public void settingsHandler(View view){
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    TextView score_player1, score_player2;

    TextView countdownText;
    CountDownTimer Timer;
    long time_left_in_ms;

    private int tada, fail;

    int flag_player;



    ImageView image_11_intermediate, iv_12_intermediate, iv_13_intermediate, iv_14_intermediate, iv_21_intermediate, iv_22_intermediate, iv_23_intermediate, iv_24_intermediate, iv_31_intermediate, iv_32_intermediate, iv_33_intermediate, iv_34_intermediate;
    ImageView iv_11_easy, iv_12_easy,iv_13_easy,iv_21_easy,iv_22_easy,iv_23_easy;
    ImageView iv_11_difficult, iv_12_difficult ,iv_13_difficult, iv_14_difficult,iv_21_difficult, iv_22_difficult ,iv_23_difficult, iv_24_difficult,iv_31_difficult, iv_32_difficult ,iv_33_difficult, iv_34_difficult,iv_41_difficult, iv_42_difficult ,iv_43_difficult, iv_44_difficult;
    Integer[] cardsArray_intermediate = {101,201, 102, 202, 103, 203, 104, 204, 105, 205, 106, 206};
    Integer[] cardsArray_easy = {101,201,102,202,103,203};
    Integer[] cardsArray_difficult = {101,201, 102, 202, 103, 203, 104, 204, 105, 205, 106, 206, 107, 207, 108, 208};
    int image101, image102,image103,image104,image105,image106, image107, image108, image201, image202,image203,image204,image205,image206, image207, image208;

    View layout_easy,layout_intermediate, layout_difficult;

   int firstCard, secondCard;
   int clickedFirst, clickedSecond;
   int cardNumber = 1;
   private int flag_difficulty;
   private int flag_topic;

   private SoundManager soundManager;



   int turn = 1;
   int playerPoints = 0, cpuPoints = 0;

    // highscore + startscreen code START:
    static boolean startScreenShown = false;
    static int score = 0;

    // score TextView
    TextView currentScore;

    public void startActivityStartScreen() {
        if(!startScreenShown) {
            Intent intent = new Intent(this, StartScreen.class);
            startActivity(intent);
        }
    }
    // returns true if new highscore, only call this when game is over!
    public boolean checkIfHighscore() {
        int first = StartScreen.firstPlace;
        int second = StartScreen.secondPlace;
        int third = StartScreen.thirdPlace;

        if (score >= first) {
            StartScreen.firstPlace = score;
            StartScreen.secondPlace = first;
            StartScreen.thirdPlace = second;
            StartScreen.saveHighscoreInPreferences();
            return true;
        } else if (score >= second) {
            StartScreen.secondPlace = score;
            StartScreen.thirdPlace = second;
            StartScreen.saveHighscoreInPreferences();
            return true;
        } else if (score >= third) {
            StartScreen.thirdPlace = score;
            StartScreen.saveHighscoreInPreferences();
            return true;
        }
        return false; // no new highscore

    }

    // increase and decrease score. Methods for now so you can add more logic into it like factoring
    // in the remaining time into it...!

    public void incScore() {
        score = score + 3;
        currentScore.setText(""+score);
    }

    public void decScore() {
        score = score - 1;
        currentScore.setText(""+score);

    }
    // highscore + startscreen code END

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Here we start the first screen, startActivity uses the a flag as a hack
        // so it only gets started once...
        startActivityStartScreen();

        setContentView(R.layout.activity_main);
        //flag = 1;

        flag_difficulty = settings.getFlag_difficulty();
        flag_topic = settings.getFlag_topic();
        flag_player = settings.getFlag_player();

        soundManager = new SoundManager(this);
        tada = soundManager.addSound(R.raw.tada);
        fail = soundManager.addSound(R.raw.fail);

        countdownText = (TextView) findViewById(R.id.TimerText);

        // resetting score to 0
        currentScore = (TextView) findViewById(R.id.currentScore);
        score = 0;


        if(flag_player==1){
            countdownText.setVisibility(View.VISIBLE);
            if(flag_difficulty==1){
                time_left_in_ms=600000;
            }
            else if(flag_difficulty==2){
                time_left_in_ms=150000;
            }
            else if(flag_difficulty==3){
                time_left_in_ms=60000;
            }
            Timer = new CountDownTimer(time_left_in_ms, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time_left_in_ms = millisUntilFinished;

                    updateTimer();
                }

                @Override
                public void onFinish() {

                }
            };
            Timer.start();
        }
        else {
            countdownText.setVisibility(View.INVISIBLE);
            //Timer.cancel();
        }






        layout_difficult = (View) findViewById(R.id.layout_difficult);
        layout_easy = (View) findViewById(R.id.layout_easy);
        layout_intermediate = (View) findViewById(R.id.layout_intermediate);


        score_player1 = (TextView) findViewById(R.id.score_player1);
        score_player2 = (TextView) findViewById(R.id.score_player2);

        if(flag_player==1){
            score_player1.setVisibility(View.INVISIBLE);
            score_player2.setVisibility(View.INVISIBLE);
        }
        else {
            score_player1.setVisibility(View.VISIBLE);
            score_player2.setVisibility(View.VISIBLE);
        }

        iv_11_easy = (ImageView) findViewById(R.id.iv_11_easy);
        iv_12_easy = (ImageView) findViewById(R.id.iv_12_easy);
        iv_13_easy = (ImageView) findViewById(R.id.iv_13_easy);



        iv_21_easy = (ImageView) findViewById(R.id.iv_21_easy);
        iv_22_easy = (ImageView) findViewById(R.id.iv_22_easy);
        iv_23_easy = (ImageView) findViewById(R.id.iv_23_easy);



        image_11_intermediate = (ImageView) findViewById(R.id.iv_11_intermediate);
        iv_12_intermediate = (ImageView) findViewById(R.id.iv_12_intermediate);
        iv_13_intermediate = (ImageView) findViewById(R.id.iv_13_intermediate);
        iv_14_intermediate = (ImageView) findViewById(R.id.iv_14_intermediate);


        iv_21_intermediate = (ImageView) findViewById(R.id.iv_21_intermediate);
        iv_22_intermediate = (ImageView) findViewById(R.id.iv_22_intermediate);
        iv_23_intermediate = (ImageView) findViewById(R.id.iv_23_intermediate);
        iv_24_intermediate = (ImageView) findViewById(R.id.iv_24_intermediate);

        iv_31_intermediate = (ImageView) findViewById(R.id.iv_31_intermediate);
        iv_32_intermediate = (ImageView) findViewById(R.id.iv_32_intermediate);
        iv_33_intermediate = (ImageView) findViewById(R.id.iv_33_intermediate);
        iv_34_intermediate = (ImageView) findViewById(R.id.iv_34_intermediate);

        iv_11_difficult = (ImageView) findViewById(R.id.iv_11_difficult);
        iv_12_difficult = (ImageView) findViewById(R.id.iv_12_difficult);
        iv_13_difficult = (ImageView) findViewById(R.id.iv_13_difficult);
        iv_14_difficult = (ImageView) findViewById(R.id.iv_14_difficult);

        iv_21_difficult = (ImageView) findViewById(R.id.iv_21_difficult);
        iv_22_difficult = (ImageView) findViewById(R.id.iv_22_difficult);
        iv_23_difficult = (ImageView) findViewById(R.id.iv_23_difficult);
        iv_24_difficult = (ImageView) findViewById(R.id.iv_24_difficult);

        iv_31_difficult = (ImageView) findViewById(R.id.iv_31_difficult);
        iv_32_difficult = (ImageView) findViewById(R.id.iv_32_difficult);
        iv_33_difficult = (ImageView) findViewById(R.id.iv_33_difficult);
        iv_34_difficult = (ImageView) findViewById(R.id.iv_34_difficult);

        iv_41_difficult = (ImageView) findViewById(R.id.iv_41_difficult);
        iv_42_difficult = (ImageView) findViewById(R.id.iv_42_difficult);
        iv_43_difficult = (ImageView) findViewById(R.id.iv_43_difficult);
        iv_44_difficult = (ImageView) findViewById(R.id.iv_44_difficult);



        if (flag_difficulty==1){
            layout_intermediate.setVisibility(View.GONE);
            layout_difficult.setVisibility(View.GONE);
            layout_easy.setVisibility(View.VISIBLE);

            iv_11_easy.setTag("0"); //Tag wird benötigt um am Schluss auszulesen welche Karte es ist
            iv_12_easy.setTag("1");
            iv_13_easy.setTag("2");

            iv_21_easy.setTag("3");
            iv_22_easy.setTag("4");
            iv_23_easy.setTag("5");

            frontOfCardsRessources(); // den Imageviews werden Karten zugeordnet

            Collections.shuffle(Arrays.asList(cardsArray_easy));

            score_player2.setTextColor(Color.GRAY);

            iv_11_easy.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_11_easy,theCard);
                }
            });
            iv_12_easy.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_12_easy,theCard);
                }
            });
            iv_13_easy.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_13_easy,theCard);

                }
            });

            iv_21_easy.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_21_easy,theCard);

                }
            });
            iv_22_easy.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_22_easy,theCard);

                }
            });
            iv_23_easy.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_23_easy,theCard);

                }
            });
        }


        else if (flag_difficulty==2){

            layout_intermediate.setVisibility(View.VISIBLE);
            layout_difficult.setVisibility(View.GONE);
            layout_easy.setVisibility(View.GONE);

            image_11_intermediate.setTag("0"); //Tag wird benötigt um am Schluss auszulesen welche Karte es ist
            iv_12_intermediate.setTag("1");
            iv_13_intermediate.setTag("2");
            iv_14_intermediate.setTag("3");


            iv_21_intermediate.setTag("4");
            iv_22_intermediate.setTag("5");
            iv_23_intermediate.setTag("6");
            iv_24_intermediate.setTag("7");

            iv_31_intermediate.setTag("8");
            iv_32_intermediate.setTag("9");
            iv_33_intermediate.setTag("10");
            iv_34_intermediate.setTag("11");

            frontOfCardsRessources(); // den Imageviews werden Karten zugeordnet

            Collections.shuffle(Arrays.asList(cardsArray_intermediate));

            score_player2.setTextColor(Color.GRAY);

            image_11_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(image_11_intermediate,theCard);
                }
            });
            iv_12_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_12_intermediate,theCard);
                }
            });
            iv_13_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_13_intermediate,theCard);

                }
            });

            iv_14_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_14_intermediate,theCard);

                }
            });


            iv_21_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_21_intermediate,theCard);

                }
            });
            iv_22_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_22_intermediate,theCard);

                }
            });
            iv_23_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_23_intermediate,theCard);

                }
            });

            iv_24_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_24_intermediate,theCard);

                }
            });

            iv_31_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_31_intermediate,theCard);

                }
            });
            iv_32_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_32_intermediate,theCard);

                }
            });
            iv_33_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_33_intermediate,theCard);

                }
            });
            iv_34_intermediate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_34_intermediate,theCard);

                }
            });
        }





        else if (flag_difficulty==3){
            layout_intermediate.setVisibility(View.GONE);
            layout_difficult.setVisibility(View.VISIBLE);
            layout_easy.setVisibility(View.GONE);

            iv_11_difficult.setTag("0"); //Tag wird benötigt um am Schluss auszulesen welche Karte es ist
            iv_12_difficult.setTag("1");
            iv_13_difficult.setTag("2");
            iv_14_difficult.setTag("3");


            iv_21_difficult.setTag("4");
            iv_22_difficult.setTag("5");
            iv_23_difficult.setTag("6");
            iv_24_difficult.setTag("7");

            iv_31_difficult.setTag("8");
            iv_32_difficult.setTag("9");
            iv_33_difficult.setTag("10");
            iv_34_difficult.setTag("11");

            iv_41_difficult.setTag("12");
            iv_42_difficult.setTag("13");
            iv_43_difficult.setTag("14");
            iv_44_difficult.setTag("15");

            frontOfCardsRessources(); // den Imageviews werden Karten zugeordnet

            Collections.shuffle(Arrays.asList(cardsArray_difficult));

            score_player2.setTextColor(Color.GRAY);

            iv_11_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_11_difficult,theCard);
                }
            });
            iv_12_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_12_difficult,theCard);
                }
            });
            iv_13_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_13_difficult,theCard);

                }
            });

            iv_14_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_14_difficult,theCard);

                }
            });


            iv_21_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_21_difficult,theCard);

                }
            });
            iv_22_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_22_difficult,theCard);

                }
            });
            iv_23_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_23_difficult,theCard);

                }
            });

            iv_24_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_24_difficult,theCard);

                }
            });

            iv_31_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_31_difficult,theCard);

                }
            });
            iv_32_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_32_difficult,theCard);

                }
            });
            iv_33_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_33_difficult,theCard);

                }
            });
            iv_34_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_34_difficult,theCard);

                }
            });

            iv_41_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_41_difficult,theCard);

                }
            });
            iv_42_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_42_difficult,theCard);

                }
            });
            iv_43_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_43_difficult,theCard);

                }
            });
            iv_44_difficult.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    doStuff(iv_44_difficult,theCard);

                }
            });
        }














    }


    private void doStuff(ImageView iv, int card){

        if(flag_difficulty==1){
            if(cardsArray_easy[card] == 101){ // hier wird der Tag mit den gemischten NUmmern abgeglichen, so dass herausgefunden werden
                //kann, welches Bild sich hier gerade befindet
                iv.setImageResource(image101);
            }
            else if(cardsArray_easy[card] == 102){
                iv.setImageResource(image102);
            }
            else if(cardsArray_easy[card] == 103){
                iv.setImageResource(image103);
            }

            else if(cardsArray_easy[card] == 201){
                iv.setImageResource(image201);
            }
            else if(cardsArray_easy[card] == 202){
                iv.setImageResource(image202);
            }
            else if(cardsArray_easy[card] == 203){
                iv.setImageResource(image203);
            }

            if(cardNumber ==1){
                firstCard = cardsArray_easy[card];
                if(firstCard>200){
                    firstCard = firstCard - 100;
                }
                cardNumber = 2;
                clickedFirst = card;
                // Wir finden heraus welches Bild die erste und die zweite umgedrehte KArte enthalten
                //bzw wir finden die Nummer heraus

                iv.setEnabled(false);
            }
            else if(cardNumber ==2){
                secondCard = cardsArray_easy[card];
                if(secondCard>200){
                    secondCard = secondCard - 100;
                }
                cardNumber = 1;
                clickedSecond = card;

                iv_11_easy.setEnabled(false);
                iv_12_easy.setEnabled(false);
                iv_13_easy.setEnabled(false);

                iv_21_easy.setEnabled(false);
                iv_22_easy.setEnabled(false);
                iv_23_easy.setEnabled(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calculate();
                    }
                },1000);
            }
        }

        if(flag_difficulty ==2){

            if(cardsArray_intermediate[card] == 101){ // hier wird der Tag mit den gemischten NUmmern abgeglichen, so dass herausgefunden werden
                //kann, welches Bild sich hier gerade befindet
                iv.setImageResource(image101);
            }
            else if(cardsArray_intermediate[card] == 102){
                iv.setImageResource(image102);
            }
            else if(cardsArray_intermediate[card] == 103){
                iv.setImageResource(image103);
            }
            else if(cardsArray_intermediate[card] == 104){
                iv.setImageResource(image104);
            }
            else if(cardsArray_intermediate[card] == 105){
                iv.setImageResource(image105);
            }
            else if(cardsArray_intermediate[card] == 106){
                iv.setImageResource(image106);
            }
            else if(cardsArray_intermediate[card] == 201){
                iv.setImageResource(image201);
            }
            else if(cardsArray_intermediate[card] == 202){
                iv.setImageResource(image202);
            }
            else if(cardsArray_intermediate[card] == 203){
                iv.setImageResource(image203);
            }
            else if(cardsArray_intermediate[card] == 204){
                iv.setImageResource(image204);
            }
            else if(cardsArray_intermediate[card] == 205){
                iv.setImageResource(image205);
            }  else if(cardsArray_intermediate[card] == 206){
                iv.setImageResource(image206);
            }

            if(cardNumber ==1){
                firstCard = cardsArray_intermediate[card];
                if(firstCard>200){
                    firstCard = firstCard - 100;
                }
                cardNumber = 2;
                clickedFirst = card;
                // Wir finden heraus welches Bild die erste und die zweite umgedrehte KArte enthalten
                //bzw wir finden die Nummer heraus

                iv.setEnabled(false);
            }
            else if(cardNumber ==2){
                secondCard = cardsArray_intermediate[card];
                if(secondCard>200){
                    secondCard = secondCard - 100;
                }
                cardNumber = 1;
                clickedSecond = card;

                image_11_intermediate.setEnabled(false);
                iv_12_intermediate.setEnabled(false);
                iv_13_intermediate.setEnabled(false);
                iv_14_intermediate.setEnabled(false);

                iv_21_intermediate.setEnabled(false);
                iv_22_intermediate.setEnabled(false);
                iv_23_intermediate.setEnabled(false);
                iv_24_intermediate.setEnabled(false);

                iv_31_intermediate.setEnabled(false);
                iv_32_intermediate.setEnabled(false);
                iv_33_intermediate.setEnabled(false);
                iv_34_intermediate.setEnabled(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calculate();
                    }
                },1000);
            }
        }

        if(flag_difficulty==3){

                if(cardsArray_difficult[card] == 101){ // hier wird der Tag mit den gemischten NUmmern abgeglichen, so dass herausgefunden werden
                    //kann, welches Bild sich hier gerade befindet
                    iv.setImageResource(image101);
                }
                else if(cardsArray_difficult[card] == 102){
                    iv.setImageResource(image102);
                }
                else if(cardsArray_difficult[card] == 103){
                    iv.setImageResource(image103);
                }
                else if(cardsArray_difficult[card] == 104){
                    iv.setImageResource(image104);
                }
                else if(cardsArray_difficult[card] == 105){
                    iv.setImageResource(image105);
                }
                else if(cardsArray_difficult[card] == 106){
                    iv.setImageResource(image106);
                }
                else if(cardsArray_difficult[card] == 107){
                    iv.setImageResource(image107);
                }
                else if(cardsArray_difficult[card] == 108){
                    iv.setImageResource(image108);
                }
                else if(cardsArray_difficult[card] == 201){
                    iv.setImageResource(image201);
                }
                else if(cardsArray_difficult[card] == 202){
                    iv.setImageResource(image202);
                }
                else if(cardsArray_difficult[card] == 203){
                    iv.setImageResource(image203);
                }
                else if(cardsArray_difficult[card] == 204){
                    iv.setImageResource(image204);
                }
                else if(cardsArray_difficult[card] == 205){
                    iv.setImageResource(image205);
                }
                else if(cardsArray_difficult[card] == 206){
                    iv.setImageResource(image206);
                }
                else if(cardsArray_difficult[card] == 207){
                    iv.setImageResource(image207);
                }
                else if(cardsArray_difficult[card] == 208){
                    iv.setImageResource(image208);
                }

                if(cardNumber ==1){
                    firstCard = cardsArray_difficult[card];
                    if(firstCard>200){
                        firstCard = firstCard - 100;
                    }
                    cardNumber = 2;
                    clickedFirst = card;
                    // Wir finden heraus welches Bild die erste und die zweite umgedrehte KArte enthalten
                    //bzw wir finden die Nummer heraus

                    iv.setEnabled(false);
                }
                else if(cardNumber ==2){
                    secondCard = cardsArray_difficult[card];
                    if(secondCard>200){
                        secondCard = secondCard - 100;
                    }
                    cardNumber = 1;
                    clickedSecond = card;

                    iv_11_difficult.setEnabled(false);
                    iv_12_difficult.setEnabled(false);
                    iv_13_difficult.setEnabled(false);
                    iv_14_difficult.setEnabled(false);

                    iv_21_difficult.setEnabled(false);
                    iv_22_difficult.setEnabled(false);
                    iv_23_difficult.setEnabled(false);
                    iv_24_difficult.setEnabled(false);

                    iv_31_difficult.setEnabled(false);
                    iv_32_difficult.setEnabled(false);
                    iv_33_difficult.setEnabled(false);
                    iv_34_difficult.setEnabled(false);

                    iv_41_difficult.setEnabled(false);
                    iv_42_difficult.setEnabled(false);
                    iv_43_difficult.setEnabled(false);
                    iv_44_difficult.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            calculate();
                        }
                    },1000);
                }


        }


    }

    private void calculate(){

        if(flag_difficulty==1){
            if(firstCard == secondCard){
                soundManager.play(tada);
                incScore();
                if(clickedFirst == 0){
                    iv_11_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedFirst == 1){
                    iv_12_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedFirst == 2){
                    iv_13_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedFirst == 3){
                    iv_21_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedFirst == 4){
                    iv_22_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedFirst == 5){
                    iv_23_easy.setVisibility(View.INVISIBLE);
                }



                if(clickedSecond == 0){
                    iv_11_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedSecond == 1){
                    iv_12_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedSecond == 2){
                    iv_13_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedSecond == 3){
                    iv_21_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedSecond == 4){
                    iv_22_easy.setVisibility(View.INVISIBLE);
                }
                else if (clickedSecond == 5){
                    iv_23_easy.setVisibility(View.INVISIBLE);
                }

                if(turn ==1&&flag_player==2){
                    playerPoints++;
                    score_player1.setText("P1: " + playerPoints);
                }
                else  if(turn ==2&&flag_player==2){
                    cpuPoints++;
                    score_player2.setText("P2: " + cpuPoints);
                }
            }
            else{
                soundManager.play(fail);
                decScore();
                iv_11_easy.setImageResource(R.drawable.background);
                iv_12_easy.setImageResource(R.drawable.background);
                iv_13_easy.setImageResource(R.drawable.background);


                iv_21_easy.setImageResource(R.drawable.background);
                iv_22_easy.setImageResource(R.drawable.background);
                iv_23_easy.setImageResource(R.drawable.background);


                if(turn == 1){
                    turn = 2;
                    score_player1.setTextColor(Color.GRAY);
                    score_player2.setTextColor(Color.BLACK);
                }
                else if(turn == 2){
                    turn = 1;
                    score_player2.setTextColor(Color.GRAY);
                    score_player1.setTextColor(Color.BLACK);
                }
            }

            iv_11_easy.setEnabled(true);
            iv_12_easy.setEnabled(true);
            iv_13_easy.setEnabled(true);

            iv_21_easy.setEnabled(true);
            iv_22_easy.setEnabled(true);
            iv_23_easy.setEnabled(true);


            checkEnd();
        }

        else if(flag_difficulty==2) {
            //Wenn die Nummern übereinstimmen, werden beide Karten unsichtbar gemacht
            if (firstCard == secondCard) {
                soundManager.play(tada);
                incScore();
                if (clickedFirst == 0) {
                    image_11_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 1) {
                    iv_12_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 2) {
                    iv_13_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 3) {
                    iv_14_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 4) {
                    iv_21_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 5) {
                    iv_22_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 6) {
                    iv_23_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 7) {
                    iv_24_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 8) {
                    iv_31_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 9) {
                    iv_32_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 10) {
                    iv_33_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 11) {
                    iv_34_intermediate.setVisibility(View.INVISIBLE);
                }


                if (clickedSecond == 0) {
                    image_11_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 1) {
                    iv_12_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 2) {
                    iv_13_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 3) {
                    iv_14_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 4) {
                    iv_21_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 5) {
                    iv_22_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 6) {
                    iv_23_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 7) {
                    iv_24_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 8) {
                    iv_31_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 9) {
                    iv_32_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 10) {
                    iv_33_intermediate.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 11) {
                    iv_34_intermediate.setVisibility(View.INVISIBLE);
                }

                if (turn == 1&&flag_player==2) {
                    playerPoints++;
                    score_player1.setText("P1: " + playerPoints);
                } else if (turn == 2&&flag_player==2) {
                    cpuPoints++;
                    score_player2.setText("P2: " + cpuPoints);
                }
            } else {
                soundManager.play(fail);
                decScore();
                image_11_intermediate.setImageResource(R.drawable.background);
                iv_12_intermediate.setImageResource(R.drawable.background);
                iv_13_intermediate.setImageResource(R.drawable.background);
                iv_14_intermediate.setImageResource(R.drawable.background);

                iv_21_intermediate.setImageResource(R.drawable.background);
                iv_22_intermediate.setImageResource(R.drawable.background);
                iv_23_intermediate.setImageResource(R.drawable.background);
                iv_24_intermediate.setImageResource(R.drawable.background);

                iv_31_intermediate.setImageResource(R.drawable.background);
                iv_32_intermediate.setImageResource(R.drawable.background);
                iv_33_intermediate.setImageResource(R.drawable.background);
                iv_34_intermediate.setImageResource(R.drawable.background);

                if (turn == 1) {
                    turn = 2;
                    score_player1.setTextColor(Color.GRAY);
                    score_player2.setTextColor(Color.BLACK);
                } else if (turn == 2) {
                    turn = 1;
                    score_player2.setTextColor(Color.GRAY);
                    score_player1.setTextColor(Color.BLACK);
                }
            }

            image_11_intermediate.setEnabled(true);
            iv_12_intermediate.setEnabled(true);
            iv_13_intermediate.setEnabled(true);
            iv_14_intermediate.setEnabled(true);

            iv_21_intermediate.setEnabled(true);
            iv_22_intermediate.setEnabled(true);
            iv_23_intermediate.setEnabled(true);
            iv_24_intermediate.setEnabled(true);

            iv_31_intermediate.setEnabled(true);
            iv_32_intermediate.setEnabled(true);
            iv_33_intermediate.setEnabled(true);
            iv_34_intermediate.setEnabled(true);

            checkEnd();
        }
        else if(flag_difficulty==3){

            //Wenn die Nummern übereinstimmen, werden beide Karten unsichtbar gemacht
            if (firstCard == secondCard) {
                soundManager.play(tada);
                incScore();
                if (clickedFirst == 0) {
                    iv_11_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 1) {
                    iv_12_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 2) {
                    iv_13_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 3) {
                    iv_14_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 4) {
                    iv_21_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 5) {
                    iv_22_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 6) {
                    iv_23_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 7) {
                    iv_24_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 8) {
                    iv_31_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 9) {
                    iv_32_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 10) {
                    iv_33_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 11) {
                    iv_34_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 12) {
                    iv_41_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 13) {
                    iv_42_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 14) {
                    iv_43_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 15) {
                    iv_44_difficult.setVisibility(View.INVISIBLE);
                }


                if (clickedSecond == 0) {
                    iv_11_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 1) {
                    iv_12_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 2) {
                    iv_13_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 3) {
                    iv_14_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 4) {
                    iv_21_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 5) {
                    iv_22_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 6) {
                    iv_23_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 7) {
                    iv_24_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 8) {
                    iv_31_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 9) {
                    iv_32_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 10) {
                    iv_33_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 11) {
                    iv_34_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 12) {
                    iv_41_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 13) {
                    iv_42_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 14) {
                    iv_43_difficult.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 15) {
                    iv_44_difficult.setVisibility(View.INVISIBLE);
                }

                if (turn == 1&&flag_player==2) {
                    playerPoints++;
                    score_player1.setText("P1: " + playerPoints);
                } else if (turn == 2&&flag_player==2) {
                    cpuPoints++;
                    score_player2.setText("P2: " + cpuPoints);
                }
            } else {
                soundManager.play(fail);
                decScore();
                iv_11_difficult.setImageResource(R.drawable.background);
                iv_12_difficult.setImageResource(R.drawable.background);
                iv_13_difficult.setImageResource(R.drawable.background);
                iv_14_difficult.setImageResource(R.drawable.background);

                iv_21_difficult.setImageResource(R.drawable.background);
                iv_22_difficult.setImageResource(R.drawable.background);
                iv_23_difficult.setImageResource(R.drawable.background);
                iv_24_difficult.setImageResource(R.drawable.background);

                iv_31_difficult.setImageResource(R.drawable.background);
                iv_32_difficult.setImageResource(R.drawable.background);
                iv_33_difficult.setImageResource(R.drawable.background);
                iv_34_difficult.setImageResource(R.drawable.background);

                iv_41_difficult.setImageResource(R.drawable.background);
                iv_42_difficult.setImageResource(R.drawable.background);
                iv_43_difficult.setImageResource(R.drawable.background);
                iv_44_difficult.setImageResource(R.drawable.background);

                if (turn == 1) {
                    turn = 2;
                    score_player1.setTextColor(Color.GRAY);
                    score_player2.setTextColor(Color.BLACK);
                } else if (turn == 2) {
                    turn = 1;
                    score_player2.setTextColor(Color.GRAY);
                    score_player1.setTextColor(Color.BLACK);
                }
            }

            iv_11_difficult.setEnabled(true);
            iv_12_difficult.setEnabled(true);
            iv_13_difficult.setEnabled(true);
            iv_14_difficult.setEnabled(true);

            iv_21_difficult.setEnabled(true);
            iv_22_difficult.setEnabled(true);
            iv_23_difficult.setEnabled(true);
            iv_24_difficult.setEnabled(true);

            iv_31_difficult.setEnabled(true);
            iv_32_difficult.setEnabled(true);
            iv_33_difficult.setEnabled(true);
            iv_34_difficult.setEnabled(true);

            iv_41_difficult.setEnabled(true);
            iv_42_difficult.setEnabled(true);
            iv_43_difficult.setEnabled(true);
            iv_44_difficult.setEnabled(true);


            checkEnd();
        }



    }

    private void checkEnd(){

       // checkIfHighscore();
        boolean isHighscore = false;
        String highscoreMessage = "\nNew Highscore: "+score+"!";
        if(flag_difficulty==1){
            if(iv_11_easy.getVisibility() == View.INVISIBLE &&
                    iv_12_easy.getVisibility() == View.INVISIBLE &&
                    iv_13_easy.getVisibility() == View.INVISIBLE &&
                    iv_21_easy.getVisibility() == View.INVISIBLE &&
                    iv_22_easy.getVisibility() == View.INVISIBLE &&
                    iv_23_easy.getVisibility() == View.INVISIBLE){

                isHighscore = checkIfHighscore();
                int first = StartScreen.firstPlace;
                int second = StartScreen.secondPlace;
                int third = StartScreen.thirdPlace;
                if(flag_player==1){
                    // only check and set highscore for singleplayer

                    Timer.cancel();
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


                if(flag_player==2){
                    alertDialogBuilder.setMessage("GAME OVER\nP1: " + playerPoints + " \nP2: " +cpuPoints + "\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                }
               else{
                   if(isHighscore) {
                       alertDialogBuilder.setMessage("GAME OVER"+highscoreMessage + "\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                   } else {
                       alertDialogBuilder.setMessage("GAME OVER\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                   }
                }

                alertDialogBuilder.setCancelable(false).setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                        .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }
        if(flag_difficulty==2){
            if(image_11_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_12_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_13_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_14_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_21_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_22_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_23_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_24_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_31_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_32_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_33_intermediate.getVisibility() == View.INVISIBLE &&
                    iv_34_intermediate.getVisibility() == View.INVISIBLE){

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                isHighscore = checkIfHighscore();
                int first = StartScreen.firstPlace;
                int second = StartScreen.secondPlace;
                int third = StartScreen.thirdPlace;
                if(flag_player==1){
                    Timer.cancel();
                }
                if(flag_player==2){

                    alertDialogBuilder.setMessage("GAME OVER\nP1: " + playerPoints + "\nP2: " +cpuPoints +"\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                }
                else {
                    if(isHighscore) {
                        alertDialogBuilder.setMessage("GAME OVER"+highscoreMessage +"\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                    } else {
                        alertDialogBuilder.setMessage("GAME OVER"+ "\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                    }
                }

                alertDialogBuilder.setCancelable(false).setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                        .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        if(flag_difficulty==3){
            if(iv_11_difficult.getVisibility() == View.INVISIBLE &&
                    iv_12_difficult.getVisibility() == View.INVISIBLE &&
                    iv_13_difficult.getVisibility() == View.INVISIBLE &&
                    iv_14_difficult.getVisibility() == View.INVISIBLE &&
                    iv_21_difficult.getVisibility() == View.INVISIBLE &&
                    iv_22_difficult.getVisibility() == View.INVISIBLE &&
                    iv_23_difficult.getVisibility() == View.INVISIBLE &&
                    iv_24_difficult.getVisibility() == View.INVISIBLE &&
                    iv_31_difficult.getVisibility() == View.INVISIBLE &&
                    iv_32_difficult.getVisibility() == View.INVISIBLE &&
                    iv_33_difficult.getVisibility() == View.INVISIBLE &&
                    iv_34_difficult.getVisibility() == View.INVISIBLE &&
                    iv_41_difficult.getVisibility() == View.INVISIBLE &&
                    iv_42_difficult.getVisibility() == View.INVISIBLE &&
                    iv_43_difficult.getVisibility() == View.INVISIBLE &&
                    iv_44_difficult.getVisibility() == View.INVISIBLE){

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                isHighscore = checkIfHighscore();
                int first = StartScreen.firstPlace;
                int second = StartScreen.secondPlace;
                int third = StartScreen.thirdPlace;
                if(flag_player==1){
                    Timer.cancel();
                }
                if(flag_player==2){
                    alertDialogBuilder.setMessage("GAME OVER\nP1: " + playerPoints + "\nP2: " +cpuPoints+ "\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                }
                else {
                    if(isHighscore) {
                        alertDialogBuilder.setMessage("GAME OVER"+highscoreMessage + "\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                    } else {
                        alertDialogBuilder.setMessage("GAME OVER" + "\n\nHighscore: \n1: " +first + "\n2: " + second + "\n3: "+ third);
                    }
                }
                alertDialogBuilder.setCancelable(false).setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                        .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

    }
    private void frontOfCardsRessources(){
        if(flag_difficulty==1){
            if(flag_topic==1){
                image101 = R.drawable.banana1;
                image102 = R.drawable.apple11;
                image103 = R.drawable.blackberry1;

                image201= R.drawable.banana1;
                image202 = R.drawable.apple12;
                image203 = R.drawable.blackberry2;
            }
            if(flag_topic==2){
                image101 = R.drawable.camel1;
                image102 = R.drawable.lion1;
                image103 = R.drawable.panda1;

                image201 = R.drawable.camel2;
                image202 = R.drawable.lion2;
                image203 = R.drawable.panda2;
            }
            if(flag_topic==3){
                image101 = R.drawable.airplane1;
                image102 = R.drawable.train11;
                image103 = R.drawable.ship11;

                image201 = R.drawable.airplane2;
                image202 = R.drawable.train12;
                image203 = R.drawable.ship12;
            }

        }
        else if(flag_difficulty==2){

            if(flag_topic==1){
                image101 = R.drawable.banana1;
                image102 = R.drawable.apple11;
                image103 = R.drawable.blackberry1;
                image104 = R.drawable.strawberries1;
                image105 = R.drawable.blueberry1;
                image106 = R.drawable.apple21;

                image201= R.drawable.banana1;
                image202 = R.drawable.apple12;
                image203 = R.drawable.blackberry2;
                image204 = R.drawable.strawberries2;
                image205 = R.drawable.blueberry2;
                image206 = R.drawable.apple22;
            }

            if(flag_topic==2){
                image101 = R.drawable.camel1;
                image102 = R.drawable.lion1;
                image103 = R.drawable.panda1;
                image104 = R.drawable.pigs1;
                image105 = R.drawable.bird21;
                image106 = R.drawable.racoon1;

                image201 = R.drawable.camel2;
                image202 = R.drawable.lion2;
                image203 = R.drawable.panda2;
                image204 = R.drawable.pigs2;
                image205 = R.drawable.bird22;
                image206 = R.drawable.racoon2;
            }
            if(flag_topic==3){
                image101 = R.drawable.airplane1;
                image102 = R.drawable.train11;
                image103 = R.drawable.ship11;
                image104 = R.drawable.bus11;
                image105 = R.drawable.bus21;
                image106 = R.drawable.plane1;

                image201 = R.drawable.airplane2;
                image202 = R.drawable.train12;
                image203 = R.drawable.ship12;
                image204 = R.drawable.bus12;
                image205 = R.drawable.bus22;
                image206 = R.drawable.plane2;
            }

        }
        else if(flag_difficulty==3){
            if(flag_topic==1){
                image101 = R.drawable.banana1;
                image102 = R.drawable.apple11;
                image103 = R.drawable.blackberry1;
                image104 = R.drawable.strawberries1;
                image105 = R.drawable.blueberry1;
                image106 = R.drawable.apple21;
                image107 = R.drawable.oranges11;
                image108 = R.drawable.oranges21;

                image201 = R.drawable.banana1;
                image202 = R.drawable.apple12;
                image203 = R.drawable.blackberry2;
                image204 = R.drawable.strawberries2;
                image205 = R.drawable.blueberry2;
                image206 = R.drawable.apple22;
                image207 = R.drawable.oranges12;
                image208 = R.drawable.oranges22;
            }
            else if(flag_topic==2){
                image101 = R.drawable.camel1;
                image102 = R.drawable.lion1;
                image103 = R.drawable.panda1;
                image104 = R.drawable.pigs1;
                image105 = R.drawable.bird21;
                image106 = R.drawable.racoon1;
                image107 = R.drawable.bird11;
                image108 = R.drawable.tiger1;

                image201 = R.drawable.camel2;
                image202 = R.drawable.lion2;
                image203 = R.drawable.panda2;
                image204 = R.drawable.pigs2;
                image205 = R.drawable.bird22;
                image206 = R.drawable.racoon2;
                image207 = R.drawable.bird12;
                image208 = R.drawable.tiger2;
            }
            else if(flag_topic==3){
                image101 = R.drawable.airplane1;
                image102 = R.drawable.train11;
                image103 = R.drawable.ship11;
                image104 = R.drawable.bus11;
                image105 = R.drawable.bus21;
                image106 = R.drawable.plane1;
                image107 = R.drawable.train21;
                image108 = R.drawable.ship21;

                image201 = R.drawable.airplane2;
                image202 = R.drawable.train12;
                image203 = R.drawable.ship12;
                image204 = R.drawable.bus12;
                image205 = R.drawable.bus22;
                image206 = R.drawable.plane2;
                image207 = R.drawable.train22;
                image208 = R.drawable.ship22;
            }

        }




    }
    public void updateTimer(){
        int minutes = (int) time_left_in_ms/60000;
        int seconds = (int) time_left_in_ms% 60000/1000;
         String timeLeftText;
         timeLeftText = "" + minutes;
         timeLeftText+=":";

         if (seconds<10){
             timeLeftText+="0";
        }
        timeLeftText+=seconds;
         countdownText.setText(timeLeftText);

        if(minutes==0 && seconds==0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setMessage("GAME OVER\n Time is up, you lose").setCancelable(false).setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        }
    }

