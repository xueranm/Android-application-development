package com.example.xueranma.nogamenolife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xueranma on 7/23/17.
 */

public class NewTaskActivity extends Activity implements OnClickListener {
    EditText taskDesEditText;
    EditText setPoint2EditText;
    Button submitTasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);

        taskDesEditText = (EditText)findViewById(R.id.taskDesEditText);
        setPoint2EditText = (EditText)findViewById(R.id.setPoint2EditText);
        submitTasButton = (Button)findViewById(R.id.submitTasButton);
        submitTasButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.submitTasButton){
            if(setPoint2EditText.getText().toString().equals("")){
                Toast.makeText(this, "Please enter a point!", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent();
                intent.putExtra("description",taskDesEditText.getText().toString());
                intent.putExtra("value",setPoint2EditText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }

        }
    }
}
