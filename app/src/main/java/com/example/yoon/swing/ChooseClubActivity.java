package com.example.yoon.swing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.yoon.swing.RecordActivity.cal;

public class ChooseClubActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_club);
        linearLayout = (LinearLayout)findViewById(R.id.activity_choose_club);
        SettingBackground();

    }
    public void SettingBackground(){
        cal = Calendar.getInstance();
        String strDateFormat = "HH";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String hour_string = sdf.format(cal.getTime());
        int hour = Integer.parseInt(hour_string);
        Log.d("HOUR : ", hour_string);
        if(hour >= 6 && hour < 14 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 1){
            linearLayout.setBackgroundResource(R.drawable.mainback);
        }else if(hour >= 14 && hour < 18 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 2){
            linearLayout.setBackgroundResource(R.drawable.background12);
        }else if(hour >= 18 && hour <6 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 3){
            linearLayout.setBackgroundResource(R.drawable.background9);
        }
    }

    public void club1Click(View view) {
        Toast.makeText(this, "club1",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,PlayActivity.class);
        intent.putExtra("CLUB", 1);
        startActivity(intent);
        finish();
    }

    public void club7Click(View view) {
        Toast.makeText(this, "club7",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,PlayActivity.class);
        intent.putExtra("CLUB",7);
        startActivity(intent);
        finish();
    }
}
