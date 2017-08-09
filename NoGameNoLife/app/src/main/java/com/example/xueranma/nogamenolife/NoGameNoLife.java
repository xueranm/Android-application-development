package com.example.xueranma.nogamenolife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NoGameNoLife extends Activity implements OnClickListener {

    private int totalPoint;
    private boolean banNegativePoint;

    TextView totalPointTextView;
    Button consumeButton;
    Button earnButton;

    SharedPreferences prefs;
    SharedPreferences prefsSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_game_no_life);

        totalPointTextView = (TextView)findViewById(R.id.totalPointTextView);
        consumeButton = (Button)findViewById(R.id.consumeButton);
        earnButton = (Button)findViewById(R.id.earnButton);
        consumeButton.setOnClickListener(this);
        earnButton.setOnClickListener(this);
        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this,
                R.xml.preferences, false);

        // get the default SharedPreferences object
        prefsSetting = PreferenceManager.getDefaultSharedPreferences(this);

        prefs = getSharedPreferences("NoGameNoLife", 0);
    }

    @Override
    public void onPause(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("totalPoint",totalPoint);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();


        //get the instance variables
        totalPoint = prefs.getInt("totalPoint",0);
        totalPointTextView.setText(Integer.toString(totalPoint));

        //get setting preferences
        banNegativePoint = prefsSetting.getBoolean("ban_negative_point",false);
        if(banNegativePoint==true&&totalPoint<0){
            consumeButton.setEnabled(false);
        }else {
            consumeButton.setEnabled(true);
        }


    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.consumeButton){
            Intent intent = new Intent(this, ConsumeActivity.class);
            intent.putExtra("totalPoint", totalPoint);  // send data to consume activity
            startActivityForResult(intent,1);
        }else if(v.getId()==R.id.earnButton){
            Intent intent = new Intent(this, EarnActivity.class);
            intent.putExtra("totalPoint", totalPoint);  // send data to consume activity
            startActivityForResult(intent,2);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_clear) {
            Toast.makeText(this, "Now clear the total point! New beginning!", Toast.LENGTH_LONG).show();
            totalPoint = 0;
            totalPointTextView.setText(Integer.toString(totalPoint));
            consumeButton.setEnabled(true);
            return true;
        }
        else if (id == R.id.menu_about) {
            Toast.makeText(this, "This application was written by Xueran Ma", Toast.LENGTH_LONG).show();
            return true;
        }
        else if(id == R.id.menu_setting){
            startActivity(new Intent(
                    getApplicationContext(), SettingsActivity.class));
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }


}
