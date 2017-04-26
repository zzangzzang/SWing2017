package com.example.yoon.swing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;

public class ResultActivity extends AppCompatActivity {

    LineView lineView1, lineView2;
    boolean show1 = false, show2 = false;
    ArrayList<ArrayList<Integer>> dataLists1 ,dataLists2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = (TextView)findViewById(R.id.synctext);
        SpannableString syncText = new SpannableString("63%");
        syncText.setSpan(new RelativeSizeSpan(0.6f),2,3,0);
        textView.setText(syncText);
        lineView1 = (LineView)findViewById(R.id.line_view);
        lineView1.setDrawDotLine(true); //optional
        lineView1.setShowPopup(LineView.SHOW_POPUPS_NONE); //optional
        dataset1();
        lineView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show1 == false){
                    lineView1.setShowPopup(lineView1.SHOW_POPUPS_All);
                    show1 = true;
                }
                else{
                    lineView1.setShowPopup(lineView1.SHOW_POPUPS_NONE);
                    show1 = false;
                }
            }
        });
        lineView1.setDataList(dataLists1); //or lineView.setFloatDataList(floatDataLists)

        lineView2 = (LineView)findViewById(R.id.line_view2);
        lineView2.setDrawDotLine(true); //optional

        lineView2.setShowPopup(LineView.SHOW_POPUPS_NONE); //optional
        dataset2();
        lineView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show2 == false){
                    lineView2.setShowPopup(lineView2.SHOW_POPUPS_All);
                    show2 = true;
                }
                else{
                    lineView2.setShowPopup(lineView2.SHOW_POPUPS_NONE);
                    show2 = false;
                }
            }
        });
        lineView2.setDataList(dataLists2); //or lineView.setFloatDataList(floatDataLists)

    }
    void dataset1(){

        ArrayList<String> strList = new ArrayList<>();
        strList.add("1");
        strList.add("2");
        strList.add("3");
        strList.add("4");
        strList.add("5");
        strList.add("6");
        strList.add("7");
        lineView1.setBottomTextList(strList);
        lineView1.setColorArray(new int[]{Color.BLACK,Color.GRAY});

        dataLists1 = new ArrayList<>();
        ArrayList<Integer> intlist = new ArrayList<>();
        intlist.add(1);
        intlist.add(2);
        intlist.add(3);
        intlist.add(4);
        intlist.add(5);
        intlist.add(6);
        intlist.add(7);
        dataLists1.add(intlist);

        ArrayList<Integer> intlist2 = new ArrayList<>();
        intlist2.add(5);
        intlist2.add(3);
        intlist2.add(2);
        intlist2.add(3);
        intlist2.add(6);
        intlist2.add(9);
        intlist2.add(16);
        dataLists1.add(intlist2);
    }
    void dataset2() {

        ArrayList<String> strList = new ArrayList<>();
        strList.add("1");
        strList.add("2");
        strList.add("3");
        strList.add("4");
        strList.add("5");
        strList.add("6");
        strList.add("7");
        lineView2.setBottomTextList(strList);
        lineView2.setColorArray(new int[]{Color.BLACK, Color.GRAY});

        dataLists2 = new ArrayList<>();
        ArrayList<Integer> intlist = new ArrayList<>();
        intlist.add(1);
        intlist.add(2);
        intlist.add(3);
        intlist.add(4);
        intlist.add(5);
        intlist.add(6);
        intlist.add(7);
        dataLists2.add(intlist);

        ArrayList<Integer> intlist2 = new ArrayList<>();
        intlist2.add(5);
        intlist2.add(3);
        intlist2.add(2);
        intlist2.add(3);
        intlist2.add(6);
        intlist2.add(9);
        intlist2.add(16);
        dataLists2.add(intlist2);
    }

}
