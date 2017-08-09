package com.example.xueranma.nogamenolife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xueranma on 7/23/17.
 */
//task listview
public class EarnActivity extends Activity implements OnClickListener,OnItemClickListener {

    Button newTaskButton;
    TextView totalPointEarnTextView;
    ListView taskListView;

    private Items items;
    private Item perItem = null;
    private ArrayList<HashMap<String,String>> data;

    SharedPreferences prefs;
    int totalPoint;
    SimpleAdapter adapter =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earn_point_activity);

        taskListView = (ListView)findViewById(R.id.taskListView);
        totalPointEarnTextView = (TextView)findViewById(R.id.totalPointEarnTextView);
        newTaskButton = (Button)findViewById(R.id.newTaskButton);
        newTaskButton.setOnClickListener(this);

        prefs = getSharedPreferences("NoGameNoLife", 0);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        totalPoint = intent.getExtras().getInt("totalPoint");

        totalPointEarnTextView.setText("Your Total Point Now: "+Integer.toString(totalPoint));

        Set<String> des = prefs.getStringSet("des1",new HashSet<String>());
        Set<String> value = prefs.getStringSet("value1",new HashSet<String>());
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
            map.put(ConsumeActivity.DESCRIPTION,item.getDescription());
            map.put(ConsumeActivity.VALUE,Integer.toString(item.getValue()));
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
        taskListView.setAdapter(adapter);
        taskListView.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v){
        if(v.getId()==R.id.newTaskButton){
            Intent intent = new Intent(this, NewTaskActivity.class);
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
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("totalPoint",totalPoint);
        Set<String> des = new HashSet<String>();
        Set<String> value = new HashSet<String>();
        for (Item item:items){
            value.add(Integer.toString(item.getValue()));
            des.add(item.getDescription());
        }
        editor.putStringSet("des1",des);
        editor.putStringSet("value1",value);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = items.get(position);
        totalPoint = totalPoint+item.getValue();
        items.remove(position);
        data.remove(position);
        Toast.makeText(this, "One is Finished! Good Job!",
                Toast.LENGTH_LONG).show();
        totalPointEarnTextView.setText("Your Total Point Now: "+Integer.toString(totalPoint));
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
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("totalPoint",totalPoint);
        editor.commit();
        super.onBackPressed();
    }*/
}
