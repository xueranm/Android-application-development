package com.example.xueranma.nogamenolife;

/**
 * Created by xueranma on 7/6/17.
 */

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment {
        //implements OnSharedPreferenceChangeListener {

    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
 //       prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
 //       prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
 //       prefs.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
    //@Override
   // public void onSharedPreferenceChanged(SharedPreferences prefs,
    //                                      String key) {
        //SecondFragment pigFragment =
        //        (SecondFragment) getFragmentManager()
        //                .findFragmentById(R.id.second_fragment);
        //if (pigFragment != null) {
        //    pigFragment.onResume();
        //}

    //}
}