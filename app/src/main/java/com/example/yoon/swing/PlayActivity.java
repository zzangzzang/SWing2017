package com.example.yoon.swing;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class PlayActivity extends AppCompatActivity {
    public static String uri_string = "";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        TextView tvClub;
        Intent intent = getIntent();
        int myClub = intent.getIntExtra("CLUB", -1);

        tvClub = (TextView)findViewById(R.id.tv1);

        tvClub.setText("< "+String.valueOf(myClub)+"번 클럽으로 연습 중입니다 >");
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    1234);
        }

//        Toast.makeText(this, "play!!",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this,ResultActivity.class);
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

    String CAPTURE_TITLE = "test.3gp"; // 여기서 test 부분만 이름지정해서 저장해주어야함 (확장자안써서 그동안 못열었던것...^^) + 퍼미션....
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
