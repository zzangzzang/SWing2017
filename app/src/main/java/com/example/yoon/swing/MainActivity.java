package com.example.yoon.swing;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.yoon.swing.RecordActivity.cal;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{
    public static String STRING_BACKGROUND = "";
    TextView textView ;
    static Calendar cal;

    String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    int permsRequestCode = 200;

    Button btn1, btn2, btn3, btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView2);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.activity_main);
        cal = Calendar.getInstance();
        String strDateFormat = "HH";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String hour_string = sdf.format(cal.getTime());
        int hour = Integer.parseInt(hour_string);
        Log.d("HOUR : ", hour_string);
        if(hour >= 6 && hour < 14){
            linearLayout.setBackgroundResource(R.drawable.background11);
            textView.setTextColor(Color.parseColor("#555555"));
        }else if(hour >= 14 && hour < 18){
            linearLayout.setBackgroundResource(R.drawable.background12);
        }else{
            linearLayout.setBackgroundResource(R.drawable.background9);
        }

        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }

    }
    @Override

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch(permsRequestCode){
            case 200:
                boolean audioAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;

        }
    }
    public void init(){
        btn1 = (Button)findViewById(R.id.btplay);
        btn1.setOnClickListener(this);
        btn2 = (Button)findViewById(R.id.btrepeat);
        btn2.setOnClickListener(this);
        btn3 = (Button)findViewById(R.id.btrecord);
        btn3.setOnClickListener(this);
        btn4 = (Button)findViewById(R.id.btexit);
        btn4.setOnClickListener(this);

//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        dbHandler.table1_addData("T20170320112125", "0", "20170320", "180105", "01");
//        dbHandler.table1_addData("T20170320112325", "0", "20170508", "180105", "01");
//        dbHandler.table1_addData("T20170320112123", "0", "20170508", "180105", "01");
//        dbHandler.table1_addData("T20170320112127", "0", "20170508", "180105", "01");
//        dbHandler.table1_addData("T20170320112235", "0", "20170510", "180105", "01");
//        dbHandler.table1_addData("T20170320112225", "0", "20170510", "180105", "01");
//        dbHandler.table1_addData("T20170320112145", "0", "20170512", "180105", "01");
    }
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.btplay:
                intent = new Intent(this,ChooseClubActivity.class);
                startActivity(intent);
                break;
            case R.id.btrepeat:
                intent = new Intent(this,RepeatActivity.class);
                startActivity(intent);
                break;
            case R.id.btrecord:
                intent = new Intent(this,RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btexit:
                finish();
                break;
        }
    }
}
