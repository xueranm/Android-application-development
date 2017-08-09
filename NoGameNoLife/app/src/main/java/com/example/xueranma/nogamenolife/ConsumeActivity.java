package com.example.xueranma.nogamenolife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xueranma on 7/23/17.
 */

public class ConsumeActivity extends Activity implements OnClickListener, OnItemClickListener {
    Button newRewardButton;
    TextView totalPointConTextView;
    ListView rewardListView;
    private Items items;
    private Item perItem = null;
    private ArrayList<HashMap<String,String>> data;
    static final String DESCRIPTION = "Description";
    static final String VALUE = "Value";

    SharedPreferences prefs;


    int totalPoint;
    SimpleAdapter adapter =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consume_point_activity);

        newRewardButton = (Button)findViewById(R.id.newRewardButton);
        newRewardButton.setOnClickListener(this);

        totalPointConTextView = (TextView)findViewById(R.id.totalPointConTextView);
        rewardListView = (ListView)findViewById(R.id.rewardListView);


        prefs = getSharedPreferences("NoGameNoLife", 0);

    }

    @Override
    protected void onResume(){
        super.onResume();

        Toast.makeText(this, "in Consume onResume", Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        totalPoint = intent.getExtras().getInt("totalPoint");
        totalPointConTextView.setText("Your Total Point Now: "+Integer.toString(totalPoint));
        Set<String> des = prefs.getStringSet("des",new HashSet<String>());
        Set<String> value = prefs.getStringSet("value",new HashSet<String>());
        List<String> desList = new ArrayList<>(des);
        List<String> valueList = new ArrayList<>(value);
        items = new Items();
        for(int count = 0; count<desList.size(); count++){
            Item saveItem = new Item();
            saveItem.setDescription(desList.get(count));
            saveItem.setValue(Integer.parseInt(valueList.get(count)));
            items.add(saveItem);
        }

        //check if add a new rewards
        if(perItem!=null){
            items.add(perItem);
        }
        data = new ArrayList<HashMap<String, String>>();
        for(Item item:items){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put(DESCRIPTION,item.getDescription());
            map.put(VALUE,Integer.toString(item.getValue()));
            data.add(map);
        }



        //our layout
        int resource = R.layout.listview_items;
        String[] from = {"Description", "Value"};
        int [] to = {R.id.descriptionLable,R.id.pointLable};

        //create and set the adapter
        adapter =
                new SimpleAdapter(this,data, resource, from, to);

        // Pass the data adapter to the List View
        rewardListView.setAdapter(adapter);
        rewardListView.setOnItemClickListener(this);

    }



    @Override
    public void onClick(View v){
        if(v.getId()==R.id.newRewardButton){
            Intent intent = new Intent(this, NewRewardActivity.class);
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent information) {
        super.onActivityResult(requestCode, resultCode, information);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                //add new reward or task into items
                String description = information.getStringExtra("description");
                String value = information.getStringExtra("value");
                int point = Integer.parseInt(value);
                Item item = new Item();
                item.setDescription(description);
                item.setValue(point);
                perItem = item;

            }
        }
    }
    @Override
    public void onPause(){
        Editor editor = prefs.edit();
        editor.putInt("totalPoint",totalPoint);
        Set<String> des = new HashSet<String>();
        Set<String> value = new HashSet<String>();
        for (Item item:items){
            value.add(Integer.toString(item.getValue()));
            des.add(item.getDescription());
        }
        editor.putStringSet("des",des);
        editor.putStringSet("value",value);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = items.get(position);
        totalPoint = totalPoint-item.getValue();
        items.remove(position);
        data.remove(position);
        Toast.makeText(this, "One is consumed! Enjoy it!",
                Toast.LENGTH_LONG).show();
        totalPointConTextView.setText("Your Total Point Now: "+Integer.toString(totalPoint));
        adapter.notifyDataSetChanged();
    }


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Editor editor = prefs.edit();
        editor.putInt("totalPoint",totalPoint);
        editor.commit();
        super.onBackPressed();
    }*/


}
