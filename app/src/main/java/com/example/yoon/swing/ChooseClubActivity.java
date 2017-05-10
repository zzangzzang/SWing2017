package com.example.yoon.swing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ChooseClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_club);
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
