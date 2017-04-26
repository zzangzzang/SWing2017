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

//        Typeface typeface = Typeface.createFromAsset(getAssets(), "KozGoPro-Light");
//        btn1.setTypeface(typeface);
//        btn2.setTypeface(typeface);
//        btn3.setTypeface(typeface);
//        btn4.setTypeface(typeface);

    }
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.btplay:
                intent = new Intent(this,PlayActivity.class);
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
