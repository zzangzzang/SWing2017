package com.example.yoon.swing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        TextView tvClub;
        Intent intent = getIntent();
        int myClub = intent.getIntExtra("CLUB", -1);

        tvClub = (TextView)findViewById(R.id.tv1);

        tvClub.setText("< "+String.valueOf(myClub)+"번 클럽으로 연습중입니다 >");


//        Toast.makeText(this, "play!!",Toast.LENGTH_SHORT).show();
    }

    public void txtClick(View view) {
        Intent intent = new Intent(this,ResultActivity.class);
        startActivity(intent);
        finish();
    }
}
