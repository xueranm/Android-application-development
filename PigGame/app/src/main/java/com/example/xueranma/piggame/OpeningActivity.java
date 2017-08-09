package com.example.xueranma.piggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by xueranma on 7/11/17.
 */
//also FirstActivity
public class OpeningActivity extends Activity {

    private String player1String="";
    private String player2String="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.opening_activity);

    }

    // Only used when two fragments are loaded in this activity
    // This is called when the newGame button is clicked in the OpeningFragment
    public void newGame(String player1, String player2){
        //game = g;

        SecondFragment secondFragment = (SecondFragment)getFragmentManager()
                .findFragmentById(R.id.second_fragment);
        //secondFragment.setPlayersNames(player1,player2);
        secondFragment.newGame(player1,player2);
    }




}
