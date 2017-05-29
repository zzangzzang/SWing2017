package com.example.yoon.swing;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    public static String STRING_BACKGROUND = "";
    public static int ThemaType = 0;
    LinearLayout linearLayout;
    static Calendar cal;
    TextView textView;
    Typeface typeface;
    private long pressedTime;
    String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    int permsRequestCode = 200;
    MyDBHandler dbHandler;
    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = Typeface.createFromAsset(getAssets(), "nanumpen.ttf");

        textView = (TextView) findViewById(R.id.textView2);
        linearLayout = (LinearLayout) findViewById(R.id.activity_main);

        textView.setTypeface(typeface);
        SettingBackground();
        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }

    }

    public void SettingBackground() {
        cal = Calendar.getInstance();
        String strDateFormat = "HH";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String hour_string = sdf.format(cal.getTime());
        int hour = Integer.parseInt(hour_string);
        Log.d("HOUR : ", hour_string);
        if (hour >= 6 && hour < 14 && ThemaType == 0 || ThemaType == 1) {
            linearLayout.setBackgroundResource(R.drawable.mainback);
            textView.setTextColor(Color.parseColor("#ffffff"));
        } else if (hour >= 14 && hour < 18 && ThemaType == 0 || ThemaType == 2) {
            linearLayout.setBackgroundResource(R.drawable.back2);
            textView.setTextColor(Color.parseColor("#ffffff"));
        } else if ( (hour >= 18 || hour < 6 && ThemaType == 0) || ThemaType == 3) {
            linearLayout.setBackgroundResource(R.drawable.back3);
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 200:
                boolean audioAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;

        }
    }

    public void init() {
        btn1 = (Button) findViewById(R.id.btplay);
        btn1.setOnClickListener(this);
        btn1.setTypeface(typeface);
        btn2 = (Button) findViewById(R.id.btrepeat);
        btn2.setOnClickListener(this);
        btn2.setTypeface(typeface);
        btn3 = (Button) findViewById(R.id.btrecord);
        btn3.setOnClickListener(this);
        btn3.setTypeface(typeface);
        btn4 = (Button) findViewById(R.id.btexit);
        btn4.setOnClickListener(this);
        btn4.setTypeface(typeface);
        dbHandler = new MyDBHandler(this, null, null, 1);


        readDataFile();

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btplay:
                intent = new Intent(this, ChooseClubActivity.class);
                startActivity(intent);
                break;
            case R.id.btrepeat:
                intent = new Intent(this, RepeatActivity.class);
                startActivity(intent);
                break;
            case R.id.btrecord:
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btexit:
                finish();
                break;
        }
    }


    public void readDataFile() {
        Scanner scan = null;

        dbHandler.table1_addData("T20170614163502", "0", "20170614", "163502", "1");
        String TRAINING_SEQ = "";
        String DETAIL_SEQ = "";
        String REG_TIME = "";
        String RIGHT_WEIGHT = "";
        String LEFT_WEIGHT = "";

        scan = new Scanner(
                getResources().openRawResource(R.raw.userdetail1)
        );
        while (scan.hasNextLine()) {
            TRAINING_SEQ = scan.nextLine();
            DETAIL_SEQ = scan.nextLine();
            REG_TIME = scan.nextLine();
            RIGHT_WEIGHT = scan.nextLine();
            LEFT_WEIGHT = scan.nextLine();
            dbHandler.table2_addData(TRAINING_SEQ, DETAIL_SEQ, REG_TIME, RIGHT_WEIGHT, LEFT_WEIGHT);
        }
        scan.close();

        dbHandler.table1_addData("T20170614163037", "0", "20170614", "163037", "1");
        TRAINING_SEQ = "";
        DETAIL_SEQ = "";
        REG_TIME = "";
        RIGHT_WEIGHT = "";
        LEFT_WEIGHT = "";
        scan = new Scanner(
                getResources().openRawResource(R.raw.userdetail1));
        while (scan.hasNextLine()) {
            TRAINING_SEQ = scan.nextLine();
            DETAIL_SEQ = scan.nextLine();
            REG_TIME = scan.nextLine();
            RIGHT_WEIGHT = scan.nextLine();
            LEFT_WEIGHT = scan.nextLine();
            dbHandler.table2_addData(TRAINING_SEQ, DETAIL_SEQ, REG_TIME, RIGHT_WEIGHT, LEFT_WEIGHT);
        }
        scan.close();

        // 반복데이터
        dbHandler.table1_addData("T20170613163502", "1", "20170613", "163502", "1");
        TRAINING_SEQ = "";
        DETAIL_SEQ = "";
        REG_TIME = "";
        RIGHT_WEIGHT = "";
        LEFT_WEIGHT = "";
        scan = new Scanner(
                getResources().openRawResource(R.raw.repeatdata));
        while (scan.hasNextLine()) {
            TRAINING_SEQ = scan.nextLine();
            DETAIL_SEQ = scan.nextLine();
            REG_TIME = scan.nextLine();
            RIGHT_WEIGHT = scan.nextLine();
            LEFT_WEIGHT = scan.nextLine();
            dbHandler.table2_addData(TRAINING_SEQ, DETAIL_SEQ, REG_TIME, RIGHT_WEIGHT, LEFT_WEIGHT);
        }
        scan.close();
        dbHandler.table4_addData("p00001", "윤지수", "0");
        String PRO_ID = "";
        DETAIL_SEQ = "";
        RIGHT_WEIGHT = "";
        LEFT_WEIGHT = "";

        scan = new Scanner(
                getResources().openRawResource(R.raw.prodata));
        while (scan.hasNextLine()) {
            PRO_ID = scan.nextLine();
            DETAIL_SEQ = scan.nextLine();
            REG_TIME = scan.nextLine();
            RIGHT_WEIGHT = scan.nextLine();
            LEFT_WEIGHT = scan.nextLine();
            dbHandler.table5_addData(PRO_ID, DETAIL_SEQ, RIGHT_WEIGHT, LEFT_WEIGHT);
        }
        scan.close();


    }

    public void settingClick(View view) {
        final int[] select_type = {11};
        final String lists[] = {"자동", "테마1", "테마2", "테마3"};
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Thema");

        ab.setSingleChoiceItems(lists, ThemaType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        select_type[0] = 0;
                        break;
                    case 1:
                        select_type[0] = 1;
                        break;
                    case 2:
                        select_type[0] = 2;
                        break;
                    case 3:
                        select_type[0] = 3;
                        break;
                }
            }
        }).setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ThemaType = select_type[0];
                SettingBackground();
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ab.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (pressedTime == 0) {
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);
            if (seconds > 2000) {
                pressedTime = 0;
            } else {
                finish();
            }
        }
    }
}
