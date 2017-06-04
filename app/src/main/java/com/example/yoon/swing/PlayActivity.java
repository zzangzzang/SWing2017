package com.example.yoon.swing;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.yoon.swing.R.id.tv1;
import static com.example.yoon.swing.RecordActivity.cal;

public class PlayActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    String ymd, hms, CAPTURE_TITLE;
    int myClub;
    int FLAG = 0; // 일반연습

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        linearLayout = (LinearLayout)findViewById(R.id.activity_play);
        SettingBackground();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "nanumpen.ttf");

        Intent intent = getIntent();
        myClub = intent.getIntExtra("CLUB", -1);
        TextView tvClub;
        TextView textview1, textview2;
        tvClub = (TextView)findViewById(tv1);
        textview1 = (TextView)findViewById(R.id.textview);
        textview2 = (TextView)findViewById(R.id.textview2);
        tvClub.setTypeface(typeface);
        textview1.setTypeface(typeface);
        textview2.setTypeface(typeface);

        tvClub.setText("< "+String.valueOf(myClub)+"번 클럽으로 연습합니다 >");
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    1234);
        }

        Toast.makeText(this, "play!!",Toast.LENGTH_SHORT).show();
        String strDateFormat = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String strDateFormat2 = "HHmmss";
        SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat2);
        ymd = sdf.format(cal.getTime());
        hms = sdf2.format(cal.getTime());
        CAPTURE_TITLE = "T" +  ymd + hms + ".3gp";
        Toast.makeText(this, CAPTURE_TITLE, Toast.LENGTH_SHORT).show();
    }
    public void SettingBackground(){
        Calendar calNow = Calendar.getInstance();
        String strDateFormat = "HH";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String hour_string = sdf.format(calNow.getTime());
        int hour = Integer.parseInt(hour_string);
        Log.d("HOUR : ", hour_string);
        if(hour >= 6 && hour < 14 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 1){
            linearLayout.setBackgroundResource(R.drawable.mainback3);
        }else if(hour >= 14 && hour < 18 && MainActivity.ThemaType == 0 || MainActivity.ThemaType == 2){
            linearLayout.setBackgroundResource(R.drawable.back22);
        }else if((hour >= 18 || hour <6 )&& MainActivity.ThemaType == 0 || MainActivity.ThemaType == 3){
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
        myDBHandler.table1_addData(title, Integer.toString(FLAG), ymd, hms, Integer.toString(myClub), 0); // 헤더
        // 상세 넣어야함
        myDBHandler.table3_addData(title, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString(), CAPTURE_TITLE); // 동영상
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("YMD", "T" + ymd + hms);
        intent.putExtra("FLAG", 0);
        intent.putExtra("CLUB", myClub);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1234){
            Intent intent = new Intent(this,ResultActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // 여기서 test 부분만 이름지정해서 저장해주어야함 (확장자안써서 그동안 못열었던것...^^) + 퍼미션....
    public void videoClick(View view) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), CAPTURE_TITLE);

        Intent i = new Intent("android.media.action.VIDEO_CAPTURE");
        //String url = SwingApplication.getVideoResourcesStorage() + "/tv";// "/sdcard/Download/exam/" + String.valueOf(System.currentTimeMillis());
        //Log.d("URI : ", url);
        //uri_string = url;
        i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        i.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 0);
        i.putExtra("android.intent.extra.durationLimit", 60);
        startActivityForResult(i, 1234);
    }
}
