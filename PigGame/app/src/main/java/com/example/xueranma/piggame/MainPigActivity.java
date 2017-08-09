package com.example.xueranma.piggame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by xueranma on 7/11/17.
 */

public class MainPigActivity extends Activity {
    //private PigGame game;
    //set up preferences
    //private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_pig_activity);

        String player1String = getIntent().getExtras().getString("player1String");
        String player2String = getIntent().getExtras().getString("player2String");
        boolean newg = getIntent().getBooleanExtra("newGame",true);
        // Crete a new game object.
        //game = new PigGame();
        Log.d("testing","in activity newGame");
        // Pass the fragment a game ref while calling the method that invokes game play
        SecondFragment secondFragment = (SecondFragment)getFragmentManager()
                .findFragmentById(R.id.second_fragment);
        //secondFragment.onResume();//test
        secondFragment.newGame(player1String,player2String);

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("testing","in activity onResume");

    }



}
