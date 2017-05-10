package com.example.yoon.swing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{

    Button btn1, btn2, btn3, btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.table1_addData("T20170320112125", "0", "20170320", "180105", "01");
        dbHandler.table1_addData("T20170320112325", "0", "20170508", "180105", "01");
        dbHandler.table1_addData("T20170320112123", "0", "20170508", "180105", "01");
        dbHandler.table1_addData("T20170320112127", "0", "20170508", "180105", "01");
        dbHandler.table1_addData("T20170320112235", "0", "20170510", "180105", "01");
        dbHandler.table1_addData("T20170320112225", "0", "20170510", "180105", "01");
        dbHandler.table1_addData("T20170320112145", "0", "20170512", "180105", "01");
    }
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.btplay:
                intent = new Intent(this,ChooseClubActivity.class);
                startActivity(intent);
                finish();
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
