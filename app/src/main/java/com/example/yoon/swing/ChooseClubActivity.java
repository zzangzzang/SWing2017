package com.example.yoon.swing;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.yoon.swing.RecordActivity.cal;

public class ChooseClubActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    LinearLayout layout7, layout1;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_club);

        Intent intent = getIntent();
        type = 0;
        type = intent.getIntExtra("TYPE", -1);

        linearLayout = (LinearLayout)findViewById(R.id.activity_choose_club);
        SettingBackground();

        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "nanumpen.ttf");

        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);

        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 0){  // 1번 연습
                    Intent intent = new Intent(getApplicationContext(),PlayActivity.class);
                    intent.putExtra("CLUB", 1);
                    intent.putExtra("TYPE", 0);
                    startActivity(intent);
                    finish();
                }
                else if(type == 1){     // 반복연습
                    Intent intent = new Intent(getApplicationContext(),RepeatActivity.class);
                    intent.putExtra("CLUB", 1);
                    intent.putExtra("TYPE", 1);
                    startActivity(intent);
                    finish();
                }
            }
        });

        layout7 = (LinearLayout)findViewById(R.id.layout7);
        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 0){  // 1번 연습
                    Intent intent = new Intent(getApplicationContext(),PlayActivity.class);
                    intent.putExtra("CLUB",7);
                    intent.putExtra("TYPE",0);
                    startActivity(intent);
                    finish();
                }
                else if(type == 1){
                    Intent intent = new Intent(getApplicationContext(),RepeatActivity.class);
                    intent.putExtra("CLUB",7);
                    intent.putExtra("TYPE",1);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void SettingBackground(){
        cal = Calendar.getInstance();
        String strDateFormat = "HH";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String hour_string = sdf.format(cal.getTime());
        int hour = Integer.parseInt(hour_string);
        Log.d("HOUR : ", hour_string);
        if(hour >= 6 && hour < 14 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 1){
            linearLayout.setBackgroundResource(R.drawable.mainback3);
        }else if(hour >= 14 && hour < 18 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 2){
            linearLayout.setBackgroundResource(R.drawable.back22);
        }else if((hour >= 18 || hour <6) && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 3){
            linearLayout.setBackgroundResource(R.drawable.back32);
        }
    }


}
