package com.example.xueranma.piggame;

/**
 * Created by xueranma on 7/6/17.
 */
import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}