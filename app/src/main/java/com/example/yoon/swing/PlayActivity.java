package com.example.yoon.swing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toast.makeText(this, "play!!",Toast.LENGTH_SHORT).show();
    }

    public void txtClick(View view) {
        Intent intent = new Intent(this,ResultActivity.class);
        startActivity(intent);
    }
}
