package com.example.xueranma.nogamenolife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by xueranma on 7/23/17.
 */

public class NewRewardActivity extends Activity implements OnClickListener {
    EditText rewardDesEditText;
    EditText setPoint1EditText;
    Button submitRewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reward_activity);

        rewardDesEditText = (EditText)findViewById(R.id.rewardDesEditText);
        setPoint1EditText = (EditText)findViewById(R.id.setPoint1EditText);
        submitRewButton = (Button)findViewById(R.id.submitRewButton);
        submitRewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.submitRewButton){
            if (setPoint1EditText.getText().toString().equals("")){
                Toast.makeText(this, "Please enter a point!", Toast.LENGTH_LONG).show();

            }else{
                Intent intent = new Intent();
                intent.putExtra("description",rewardDesEditText.getText().toString());
                intent.putExtra("value",setPoint1EditText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }

        }
    }

}
