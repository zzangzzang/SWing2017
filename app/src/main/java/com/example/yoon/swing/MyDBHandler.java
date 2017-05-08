package com.example.yoon.swing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yoon on 2017-05-08.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "swingDB.db", factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE1 = "create table if not exists TBL_TRANNING_HEADER ("
                +"TRAINING_SEQ NVARCHAR(15) NOT NULL, " //트레이닝 SEQ(T+년월일시분초 ex: T20170320112125)
                +"TRAINING_FLG NCHAR(1) NOT NULL, "     //트레이닝 구분(0:일반연습,1:반복연습)
                +"TRAINING_YMD NVARCHAR(8) NOT NULL, "  //트레이닝 일자(ex:20170320)
                +"TRAINING_TIME NVARCHAR(6) NOT NULL, " //트레이닝 시간(ex:180105)
                +"CLUB_NUMBER NCHAR(2) NOT NULL, "      //클럽 종류 ( 골프 채 ) – 공통코드
                +"primary key(TRAINING_SEQ) ";

        String CREATE_TABLE2 = "create table if not exists TBL_TRANNING_DETAIL ("
                +"TRAINING_SEQ NVARCHAR(15) NOT NULL, " //트레이닝 SEQ(T+년월일시분초 ex: T20170320112125)   - key
                +"DETAIL_SEQ NCHAR(3) NOT NULL, "       //상세 이력 순번(1부터 순차적으로)
                +"REG_TIME NVARCHAR(20) NOT NULL, "     //밀리세컨드 까지 등록(시작시간, 끝시간 체크)
                +"RIGHT_WEIGHT INT, "                   //우측 중량
                +"LEFT_WEIGHT INT, "                    //좌측 중량
                +"primary key(TRAINING_SEQ, DETAIL_SEQ) ";

        String CREATE_TABLE3 = "create table if not exists TBL_TRAINING_VIDEO_HIST ("
                +"TRAINING_SEQ NVARCHAR(15) NOT NULL, "
                +"FILE_PATH NVARCHAR(20) NOT NULL, "
                +"FILE_NAME NVARCHAR(20) NOT NULL, "
                +"primary key(TRAINING_SEQ) ";

        String CREATE_TABLE4 = "create table if not exists TBL_PRO_DATA_HEADER\n ("
                +"PRO_ID NVARCHAR(6) NOT NULL, "
                +"PRO_NAME NVARCHAR(20) NOT NULL, "
                +"CLUB_NUMBER NCHAR(2) NOT NULL, "
                +"primary key(PRO_ID) ";

        String CREATE_TABLE5 = "create table if not exists TBL_PRO_DATA_DETAIL ("
                +"PRO_ID NVARCHAR(6) NOT NULL, "
                +"DETAIL_SEQ NVARCHAR(3) NOT NULL, "
                +"RIGHT_WEIGHT INT,"
                +"LEFT_WEIGHT INT,"
                +"PRIMARY KEY(PRO_ID, DETAIL_SEQ)";

        String CREATE_TABLE6 = "create table if not exists TBL_COMMON_CODE ("
                +"DIV_CODE NCHAR(2) NOT NULL, "
                +"TASK_CODE NCHAR(2) NOT NULL, "
                +"PRIMARY KEY(DIV_CODE, TASK_CODE)";

        String CREATE_TABLE7 = "create table if not exists TBL_PRO_VIDEO_HIST ("
                +"PRO_ID NVARCHAR(6) NOT NULL, "
                +"FILE_PATH NVARCHAR(20) NOT NULL, "
                +"FILE_NAME NVARCHAR(20) NOT NULL, "
                +"PRIMARY KEY(PRO_ID)";

        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
        db.execSQL(CREATE_TABLE5);
        db.execSQL(CREATE_TABLE6);
        db.execSQL(CREATE_TABLE7);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        //db.execSQL("drop table if exists "+DATABASE_TABLE);
        onCreate(db);

    }

}
