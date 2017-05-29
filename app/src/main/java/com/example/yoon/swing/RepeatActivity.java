package com.example.yoon.swing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.yoon.swing.RecordActivity.cal;

public class RepeatActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    String ymd, hms, CAPTURE_TITLE;
    int myClub;
    int FLAG = 1; // 반복연습

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        linearLayout = (LinearLayout)findViewById(R.id.activity_play);
        SettingBackground();

        TextView tvClub;
        Intent intent = getIntent();
        myClub = intent.getIntExtra("CLUB", -1);

        tvClub = (TextView)findViewById(R.id.tv1);

        tvClub.setText("< "+String.valueOf(myClub)+"번 클럽으로 연습 중입니다 >");

        Toast.makeText(this, "play!!",Toast.LENGTH_SHORT).show();
        String strDateFormat = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String strDateFormat2 = "HHmmss";
        SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat2);
        ymd = sdf.format(cal.getTime());
        hms = sdf2.format(cal.getTime());
        CAPTURE_TITLE = "T" +  ymd + hms + ".3gp";
        Toast.makeText(this, CAPTURE_TITLE, Toast.LENGTH_SHORT).show();
        //Log.d("년월일ㅇ시분초 : ", ymd);
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
        }else if(hour >= 18 && hour <6 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 3){
            linearLayout.setBackgroundResource(R.drawable.back32);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1234) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            }
            else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    public void txtClick(View view) {
        MyDBHandler myDBHandler = new MyDBHandler(this, null, null, 1);
        String title = "T" +  ymd + hms;
        myDBHandler.table1_addData(title, Integer.toString(FLAG), ymd, hms, Integer.toString(myClub)); // 헤더
        // 상세 넣어야함
        myDBHandler.table3_addData(title, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString(), CAPTURE_TITLE); // 동영상
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("YMD", "T" + ymd + hms);
        intent.putExtra("FLAG", 1);
        intent.putExtra("SEQ", 1);
        startActivity(intent);
        finish();
    }
}
