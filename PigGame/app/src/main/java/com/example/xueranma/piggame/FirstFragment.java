package com.example.xueranma.piggame;

import android.app.Fragment;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by xueranma on 7/11/17.
 */
//also OpeningFragment
public class FirstFragment extends Fragment implements OnClickListener {

    private boolean twoPaneLayout;
    private EditText player1EditText;
    private EditText player2EditText;
    private OpeningActivity activity;
    private boolean newg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        // Set this fragment to listen for the new game button's click event
        Button b = (Button) view.findViewById(R.id.newGameButton);
        b.setOnClickListener(this);
        ImageView bg = (ImageView)view.findViewById(R.id.backGround) ;
        bg.setImageResource(R.drawable.pigg);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(getActivity(),
                R.xml.preferences, false);

        // turn on the options menu
        setHasOptionsMenu(true);
        // Get a references from the host activity
        activity = (OpeningActivity) getActivity();
        player1EditText = (EditText) activity.findViewById(R.id.player1EditText);
        player2EditText = (EditText) activity.findViewById(R.id.player2EditText);

        // Make a new game object, use saved state if it exists
        if (savedInstanceState != null) {
            // Restore saved state
            player1EditText.setText(savedInstanceState.getString("player1String", ""));
            player2EditText.setText(savedInstanceState.getString("player2String", ""));
            //game = new PigGame();
        }


        // Check to see if FirstActivity has loaded a single or dual pane layout
        twoPaneLayout = activity.findViewById(R.id.second_fragment) != null;

    }

    @Override
    public void onSaveInstanceState(Bundle _outState){
        _outState.putString("player1String", player1EditText.getText().toString());
        _outState.putString("player2String", player2EditText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.newGameButton) {
            String player1String = player1EditText.getText().toString();
            String player2String = player2EditText.getText().toString();
            newg = true;
            if (twoPaneLayout) {
                activity.newGame(player1String, player2String);
            } else {
                Log.d("testing","in first fragment onClick");

                Intent intent = new Intent(getActivity(), MainPigActivity.class);
                intent.putExtra("newGame",newg);
                intent.putExtra("player1String", player1String);
                intent.putExtra("player2String", player2String);
                startActivity(intent);
            }
        }
    }

    @Override
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
    }




}