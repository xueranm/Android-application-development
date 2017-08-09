package com.example.xueranma.piggame;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * Created by xueranma on 7/11/17.
 */

public class SecondFragment extends Fragment implements OnClickListener {

    PigGame game = new PigGame();
    //define variables for the widgets
    TextView score1Lable;
    TextView score2Lable;
    TextView score1TextView;
    TextView score2TextView;
    TextView turnLable;
    ImageView dieImage;
    ImageView dieImage2;
    ImageView dieImage3;
    TextView pointTextView;
    Button rollDieButton;
    Button endTurnButton;

    //set up preferences
    SharedPreferences prefs;
    String player1String = "";
    String player2String = "";
    int sidesOnDie = 6;
    int numberOfDie =1;
    int winScore = 100;
    int deadSide = 1;

    boolean newg = false;


    //Remember the last roll number to display
    int rollNum = 1;
    int rollNum2 = 1;
    int rollNum3 = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        //setRetainInstance(true);

        // set the default values for the preferences
        //PreferenceManager.setDefaultValues(getActivity(),
        //        R.xml.preferences, false);

        // get the default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());

        // turn on the options menu
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        //get references to the widgets

        ImageView bg = (ImageView)view.findViewById(R.id.backGround) ;
        bg.setImageResource(R.drawable.pigg);
        score1Lable = (TextView)view.findViewById(R.id.score1Lable);
        score2Lable = (TextView)view.findViewById(R.id.score2Lable);
        score1TextView = (TextView) view.findViewById(R.id.score1TextView);
        score2TextView = (TextView) view.findViewById(R.id.score2TextView);
        turnLable = (TextView) view.findViewById(R.id.turnLable);
        dieImage = (ImageView)view.findViewById(R.id.dieImage);
        dieImage2 = (ImageView)view.findViewById(R.id.dieImage2);
        dieImage3 = (ImageView)view.findViewById(R.id.dieImage3);
        pointTextView = (TextView) view.findViewById(R.id.pointTextView);
        rollDieButton = (Button)view.findViewById(R.id.rollDieButton);
        endTurnButton = (Button)view.findViewById(R.id.endTurnButton);
        rollDieButton.setOnClickListener(this);
        endTurnButton.setOnClickListener(this);


        return view;
    }

    //public void setPlayersNames(String player1, String player2){
    //    player1String = player1;
    //    player2String = player2;
    //}

    //public void passPrefs(SharedPreferences prefs){
    //    this.prefs = prefs;
    //}

    public void newGame(String player1String, String player2String){
        //Activity activity = getActivity();   // Get a reference to the host activity
        //get references to the widgets
        Log.d("testing","in fragment newGame");
        //Toast.makeText(getActivity(),"In newGame", Toast.LENGTH_LONG).show();

        this.newg = true;
        //get preferences
        numberOfDie = Integer.parseInt(prefs.getString("pref_number_of_die","1"));
        winScore = Integer.parseInt(prefs.getString("pref_winScore","100"));
        sidesOnDie = Integer.parseInt(prefs.getString("pref_side_on_a_die","6"));
        deadSide = Integer.parseInt(prefs.getString("pref_dead_die_side","1"));

        game.setMaxDieSides(sidesOnDie);
        game.setWin_Score(winScore);
        game.setDeadSide(deadSide);

        score2Lable.setText(player1String+" 's Score");
        score2Lable.setText(player2String+" 's Score");

        this.player1String = player1String;
        this.player2String = player2String;
        score1Lable.setText(player1String+"'s Score");
        score2Lable.setText(player2String+"'s Score");
        endTurnButton.setEnabled(true);
        rollDieButton.setEnabled(true);
        game.newGame();
        updateAndDisplay();
        getView().invalidate();
    }


    @Override
    public void onPause(){
        //save the instance variables
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("player1String",player1String);
        editor.putString("player2String",player2String);
        editor.putInt("score1Number",game.getScore(Player.First));
        editor.putInt("score2Number",game.getScore(Player.Second));
        int currentPlayer = 0;
        switch (game.getCurrentPlayer()){
            case First:
                currentPlayer =1;
                break;
            case Second:
                currentPlayer = 2;
                break;
        }
        editor.putInt("currentPlayer",currentPlayer);
        editor.putInt("currentPoint",game.getCurrentPoint());
        editor.putInt("rollNum2",rollNum2);
        editor.putInt("rollNum3",rollNum3);
        editor.putInt("rollNum",rollNum);


        editor.commit();
        Log.d("testing","in fragment onPause");
        //Toast.makeText(getActivity(),"In onPause", Toast.LENGTH_LONG).show();
        super.onPause();

    }


    @Override
    public void onResume(){
        super.onResume();
        if(!newg){
            Log.d("testing","in fragment onResume");
            //Toast.makeText(getActivity(),"In onResume", Toast.LENGTH_LONG).show();
            //get preferences
            numberOfDie = Integer.parseInt(prefs.getString("pref_number_of_die","1"));
            winScore = Integer.parseInt(prefs.getString("pref_winScore","100"));
            sidesOnDie = Integer.parseInt(prefs.getString("pref_side_on_a_die","6"));

            deadSide = Integer.parseInt(prefs.getString("pref_dead_die_side","1"));
            //get the instance variables
            player1String = prefs.getString("player1String","");
            player2String = prefs.getString("player2String","");
            score1Lable.setText(player1String+"'s Score");
            score2Lable.setText(player2String+"'s Score");

            game.setMaxDieSides(sidesOnDie);
            game.setWin_Score(winScore);
            game.setDeadSide(deadSide);


            game.setScore(Player.First,prefs.getInt("score1Number",0));
            game.setScore(Player.Second,prefs.getInt("score2Number",0));

            int currentPlayer = prefs.getInt("currentPlayer",0);
            switch (currentPlayer){
                case 1:
                    game.setCurrentPlayer(Player.First);
                    break;
                case 2:
                    game.setCurrentPlayer(Player.Second);
                    break;
            }
            game.setCurrentPoint(prefs.getInt("currentPoint",0));
            rollNum = prefs.getInt("rollNum",0);
            rollNum2 = prefs.getInt("rollNum2",0);
            rollNum3 = prefs.getInt("rollNum3",0);



            //update display
            updateAndDisplay();
        }
    }


    //update all the UI elements and display them
    // except for the winner toast
    private void updateAndDisplay() {
        NumberFormat integer = NumberFormat.getIntegerInstance();
        //Toast.makeText(getActivity(),"player1's score is"+integer.format(game.getScore(Player.First)), Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),"player2's score is"+integer.format(game.getScore(Player.Second)), Toast.LENGTH_LONG).show();
        score1TextView.setText(integer.format(game.getScore(Player.First)));
        score2TextView.setText(integer.format(game.getScore(Player.Second)));
        pointTextView.setText(integer.format(game.getCurrentPoint()));

        //current player's name
        String playerName = "";
        switch (game.getCurrentPlayer()) {
            case First:
                playerName = player1String + "'s turn!";
                break;
            case Second:
                playerName = player2String + "'s turn!";
                break;
        }
        turnLable.setText(playerName);
        //if(playWithMachine&game.getCurrentPlayer()==Player.Second){
        //    Toast.makeText(this,"Computer get "+computerPoint+" for this turn!", Toast.LENGTH_LONG).show();

        //}

        //display the correct dice icon
        if(numberOfDie>=1){
            displayImage(rollNum,1);
            if(numberOfDie>=2){
                displayImage(rollNum2,2);
                if(numberOfDie==3){
                    displayImage(rollNum3,3);
                }
            }
        }

    }
    //display the winner toast on UI
    private void win(Winner winner){
        if(numberOfDie>=1){
            displayImage(rollNum,1);
            if(numberOfDie>=2){
                displayImage(rollNum2,2);
                if(numberOfDie==3){
                    displayImage(rollNum3,3);
                }
            }
        }
        NumberFormat integer = NumberFormat.getIntegerInstance();
        score1TextView.setText(integer.format(game.getScore(Player.First)));
        score2TextView.setText(integer.format(game.getScore(Player.Second)));
        pointTextView.setText(integer.format(game.getCurrentPoint()));
        switch (winner){
            case First:
                Toast.makeText(getActivity(), player1String+" Wins!", Toast.LENGTH_LONG).show();
                break;
            case Second:

                Toast.makeText(getActivity(), player2String + " Wins!", Toast.LENGTH_LONG).show();

                break;
            case Tie:
                Toast.makeText(getActivity(), "Tie!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rollDieButton:
                deadSide = game.getDeadSide();
                if (numberOfDie==1) {
                    rollNum = game.randomDie();
                    if (rollNum == deadSide) {
                        onClick(endTurnButton);
                    } else {
                        updateAndDisplay();
                    }
                }else if(numberOfDie==2){
                    rollNum = game.randomDie();
                    rollNum2 = game.randomDie();
                    if (rollNum == deadSide||rollNum2==deadSide) {
                        game.setCurrentPoint(0);
                        onClick(endTurnButton);
                    } else {
                        updateAndDisplay();
                    }

                }else if(numberOfDie==3){
                    rollNum = game.randomDie();
                    rollNum2 = game.randomDie();
                    rollNum3 =game.randomDie();
                    if (rollNum == deadSide||rollNum2==deadSide||rollNum3==deadSide) {
                        game.setCurrentPoint(0);
                        onClick(endTurnButton);
                    } else {
                        updateAndDisplay();
                    }
                }

                break;
            case R.id.endTurnButton:

                Winner winOne = game.nextTurn();
                if(winOne!=Winner.No){
                    win(winOne);
                    rollDieButton.setEnabled(false);
                    endTurnButton.setEnabled(false);
                }else{
                    updateAndDisplay();
                }


                break;
        }
    }

    //display the dice icon
    private void displayImage(int die, int rollNumber){
        int id = 0;
        switch (die){
            case 1:
                id = R.drawable.die1;
                break;
            case 2:
                id = R.drawable.die2;
                break;
            case 3:
                id = R.drawable.die3;
                break;
            case 4:
                id = R.drawable.die4;
                break;
            case 5:
                id = R.drawable.die5;
                break;
            case 6:
                id = R.drawable.die6;
                break;
        }
        if(rollNumber==1){
            dieImage.setImageResource(id);
            dieImage2.setImageResource(R.drawable.pigg);
            dieImage3.setImageResource(R.drawable.pigg);

        }else if (rollNumber==2){
            dieImage2.setImageResource(id);
            dieImage3.setImageResource(R.drawable.pigg);
        }else if (rollNumber==3){
            dieImage3.setImageResource(id);
        }


    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // attempt to get the fragment
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager()
                .findFragmentById(R.id.setting_fragment);

        inflater.inflate(R.menu.activity_main_pig, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
