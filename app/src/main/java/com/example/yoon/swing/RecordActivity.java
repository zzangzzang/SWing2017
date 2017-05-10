package com.example.yoon.swing;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoon.swing.custom.XYMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.example.yoon.swing.custom.DayAxisValueFormatter;
import com.example.yoon.swing.custom.MyAxisValueFormatter;
import com.example.yoon.swing.custom.DemoBase;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordActivity extends DemoBase {
    protected BarChart mChart;
    protected RectF mOnValueSelectedRectF;
    protected ExpandableListView listView;
    static TextView daterange;
    static Calendar cal;
    ImageButton leftarrow;
    ImageButton rightarrow;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);
        daterange = (TextView)findViewById(R.id.date_range);
        cal = Calendar.getInstance();
        leftarrow = (ImageButton)findViewById(R.id.left_arrow);
        rightarrow = (ImageButton)findViewById(R.id.right_button);

        mChart = (BarChart) findViewById(R.id.chart1);
        mOnValueSelectedRectF = new RectF();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;

                RectF bounds = mOnValueSelectedRectF;
                mChart.getBarBounds((BarEntry) e, bounds);
                MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

                Log.i("bounds", bounds.toString());
                Log.i("position", position.toString());

                Log.i("x-index",
                        "low: " + mChart.getLowestVisibleX() + ", high: "
                                + mChart.getHighestVisibleX());

                MPPointF.recycleInstance(position);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(6);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        //leftAxis.setLabelCount(7, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(6, 100);

        getCurrentWeek(cal);

        // mChart.setDrawLegend(false);
        listView = (ExpandableListView) findViewById(R.id.listView);
        prepareListData();
        listAdapter = new com.example.yoon.swing.ExpandableListAdapter(this, listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                cal.add(Calendar.DAY_OF_YEAR, -6 + i);
                String strDateFormat = "yyyyMMdd";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                String ymd = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 6 - i);
                Intent intent = new Intent(RecordActivity.this, ResultActivity.class);
                intent.putExtra("ymd", ymd);
                intent.putExtra("seq", i1);
                Toast.makeText(RecordActivity.this, ymd + ", " + i1, Toast.LENGTH_SHORT).show();

                startActivity(intent);
                return false;
            }
        });
        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastWeek(cal);
                prepareListData();
                listAdapter = new com.example.yoon.swing.ExpandableListAdapter(RecordActivity.this, listDataHeader, listDataChild);
                listView.setAdapter(listAdapter);
            }
        });
        rightarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextWeek(cal);
                prepareListData();
                listAdapter = new com.example.yoon.swing.ExpandableListAdapter(RecordActivity.this, listDataHeader, listDataChild);
                listView.setAdapter(listAdapter);
            }
        });

    }

    public static String getLastWeek(Calendar mCalendar) {
        // Monday
        mCalendar.add(Calendar.DAY_OF_YEAR, -13);
        Date mDateMonday = mCalendar.getTime();

        // Sunday
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date mDateSunday = mCalendar.getTime();

        // Date format
        String strDateFormat = "MM / dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(mDateSunday);

        String week =  MONDAY + " - " + SUNDAY;
        daterange.setText(week);

        return MONDAY + " - " + SUNDAY;
    }

    public static String getCurrentWeek(Calendar mCalendar) {
        Date date = new Date();
        mCalendar.setTime(date);

        // 1 = Sunday, 2 = Monday, etc.
        int day_of_week = mCalendar.get(Calendar.DAY_OF_WEEK);

        int monday_offset;
        if (day_of_week == 1) {
            monday_offset = -6;
        } else
            monday_offset = (2 - day_of_week); // need to minus back
        mCalendar.add(Calendar.DAY_OF_YEAR, monday_offset);

        Date mDateMonday = mCalendar.getTime();

        // return 6 the next days of current day (object cal save current day)
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date mDateSunday = mCalendar.getTime();

        //Get format date
        String strDateFormat = "MM / dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(mDateSunday);

        String week =  MONDAY + " - " + SUNDAY;
        daterange.setText(week);

        return MONDAY + " - " + SUNDAY;
    }

    public static String getNextWeek(Calendar mCalendar) {
        // Monday
        mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        Date mDateMonday = mCalendar.getTime();

        // Sunday
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date Week_Sunday_Date = mCalendar.getTime();

        // Date format
        String strDateFormat = "MM / dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(Week_Sunday_Date);

        String week =  MONDAY + " - " + SUNDAY;
        daterange.setText(week);
        return MONDAY + " - " + SUNDAY;
    }

    private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //Adding child data
        listDataHeader.add("MON");
        listDataHeader.add("TUE");
        listDataHeader.add("WED");
        listDataHeader.add("THU");
        listDataHeader.add("FRI");
        listDataHeader.add("SAT");
        listDataHeader.add("SUN");

        List<String> MON = new ArrayList<String>();
        List<String> TUE = new ArrayList<String>();
        List<String> WED = new ArrayList<String>();
        List<String> THU = new ArrayList<String>();
        List<String> FRI = new ArrayList<String>();
        List<String> SAT = new ArrayList<String>();
        List<String> SUN = new ArrayList<String>();

        cal.add(Calendar.DAY_OF_YEAR, -6);
        String strDateFormat = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String ymd = sdf.format(cal.getTime());
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String query = "select * from TBL_TRANNING_HEADER where TRAINING_YMD = \'" + ymd + "\'";
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            MON.add(String.valueOf(i + 1) + "회차");
        }

        for(int i = 0; i < 6; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            ymd = sdf.format(cal.getTime());
            query = "select * from TBL_TRANNING_HEADER where TRAINING_YMD = \'" + ymd + "\'";
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            for (int j = 0; j < cursor.getCount(); j++) {
                if(i == 0)
                    TUE.add(String.valueOf(j + 1) + "회차");
                else if(i == 1)
                    WED.add(String.valueOf(j + 1) + "회차");
                else if(i == 2)
                    THU.add(String.valueOf(j + 1) + "회차");
                else if(i == 3)
                    FRI.add(String.valueOf(j + 1) + "회차");
                else if(i == 4)
                    SAT.add(String.valueOf(j + 1) + "회차");
                else if(i == 5)
                    SUN.add(String.valueOf(j + 1) + "회차");
            }
        }

        listDataChild.put(listDataHeader.get(0), MON);
        listDataChild.put(listDataHeader.get(1), TUE);
        listDataChild.put(listDataHeader.get(2), WED);
        listDataChild.put(listDataHeader.get(3), THU);
        listDataChild.put(listDataHeader.get(4), FRI);
        listDataChild.put(listDataHeader.get(5), SAT);
        listDataChild.put(listDataHeader.get(6), SUN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet set : mChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : mChart.getData().getDataSets())
                    ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            //set1.setColor(R.color.colorPrimary);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "싱크로율");
            set1.setDrawIcons(false);
            set1.setColor(Color.argb(160, 255, 153, 153));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(0.40f);

            mChart.setData(data);
        }
    }


}