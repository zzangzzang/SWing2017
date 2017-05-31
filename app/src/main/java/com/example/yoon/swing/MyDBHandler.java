package com.example.yoon.swing;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yoon on 2017-05-08.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String TBL_TRANNING_HEADER = "TBL_TRANNING_HEADER";
    public static final String COLUMN_TRAINING_SEQ = "TRAINING_SEQ";
    public static final String COLUMN_TRAINING_FLG = "TRAINING_FLG";
    public static final String COLUMN_TRAINING_YMD = "TRAINING_YMD";
    public static final String COLUMN_TRAINING_TIME = "TRAINING_TIME";
    public static final String COLUMN_TRAINING_SYNC = "TRAINING_SYNC";

    public static final String TBL_TRANNING_DETAIL = "TBL_TRANNING_DETAIL";
    public static final String COLUMN_DETAIL_SEQ = "DETAIL_SEQ";
    public static final String COLUMN_REG_TIME = "REG_TIME";
    public static final String COLUMN_RIGHT_WEIGHT = "RIGHT_WEIGHT";
    public static final String COLUMN_LEFT_WEIGHT = "LEFT_WEIGHT";

    public static final String TBL_TRAINING_VIDEO_HIST = "TBL_TRAINING_VIDEO_HIST";
    public static final String COLUMN_FILE_PATH = "FILE_PATH";
    public static final String COLUMN_FILE_NAME = "FILE_NAME";

    public static final String TBL_PRO_DATA_HEADER = "TBL_PRO_DATA_HEADER";
    public static final String COLUMN_PRO_ID = "PRO_ID";
    public static final String COLUMN_PRO_NAME = "PRO_NAME";
    public static final String COLUMN_CLUB_NUMBER = "CLUB_NUMBER";

    public static final String TBL_PRO_DATA_DETAIL = "TBL_PRO_DATA_DETAIL";

    public static final String TBL_COMMON_CODE = "TBL_COMMON_CODE";
    public static final String COLUMN_DIV_CODE = "DIV_CODE";
    public static final String COLUMN_TASK_CODE = "TASK_CODE";

    public static final String TBL_PRO_VIDEO_HIST = "TBL_PRO_VIDEO_HIST";


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
                +"TRAINING_SYNC INT, "         // 싱크로율
                +"primary key(TRAINING_SEQ)) ";

        String CREATE_TABLE2 = "create table if not exists TBL_TRANNING_DETAIL ("
                +"TRAINING_SEQ NVARCHAR(15) NOT NULL, " //트레이닝 SEQ(T+년월일시분초 ex: T20170320112125)   - key
                +"DETAIL_SEQ NCHAR(3) NOT NULL, "       //상세 이력 순번(1부터 순차적으로) autoincrement
                +"REG_TIME NVARCHAR(20) NOT NULL, "     //밀리세컨드 까지 등록(시작시간, 끝시간 체크)
                +"RIGHT_WEIGHT INT, "                   //우측 중량
                +"LEFT_WEIGHT INT, "                    //좌측 중량
                +"primary key(TRAINING_SEQ, DETAIL_SEQ)) ";

        String CREATE_TABLE3 = "create table if not exists TBL_TRAINING_VIDEO_HIST ("
                +"TRAINING_SEQ NVARCHAR(15) NOT NULL, "
                +"FILE_PATH NVARCHAR(20) NOT NULL, "
                +"FILE_NAME NVARCHAR(20) NOT NULL, "
                +"primary key(TRAINING_SEQ)) ";

        String CREATE_TABLE4 = "create table if not exists TBL_PRO_DATA_HEADER ("
                +"PRO_ID NVARCHAR(6) NOT NULL, "
                +"PRO_NAME NVARCHAR(20) NOT NULL, "
                +"CLUB_NUMBER NCHAR(2) NOT NULL, "
                +"primary key(PRO_ID)) ";

        String CREATE_TABLE5 = "create table if not exists TBL_PRO_DATA_DETAIL ("
                +"PRO_ID NVARCHAR(6) NOT NULL, "
                +"DETAIL_SEQ NVARCHAR(3) NOT NULL, "
                +"RIGHT_WEIGHT INT,"
                +"LEFT_WEIGHT INT,"
                +"PRIMARY KEY(PRO_ID, DETAIL_SEQ))";

        String CREATE_TABLE6 = "create table if not exists TBL_COMMON_CODE ("
                +"DIV_CODE NCHAR(2) NOT NULL, "
                +"TASK_CODE NCHAR(2) NOT NULL, "
                +"PRIMARY KEY(DIV_CODE, TASK_CODE))";

        String CREATE_TABLE7 = "create table if not exists TBL_PRO_VIDEO_HIST ("
                +"PRO_ID NVARCHAR(6) NOT NULL, "
                +"FILE_PATH NVARCHAR(20) NOT NULL, "
                +"FILE_NAME NVARCHAR(20) NOT NULL, "
                +"PRIMARY KEY(PRO_ID))";

        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
        db.execSQL(CREATE_TABLE5);
        db.execSQL(CREATE_TABLE6);
        db.execSQL(CREATE_TABLE7);
    }

    public void table1_addData(String seq, String flg, String ymd, String time, String number, int sync){
        ContentValues values=new ContentValues();
        values.put(COLUMN_TRAINING_SEQ, seq);
        values.put(COLUMN_TRAINING_FLG, flg);
        values.put(COLUMN_TRAINING_YMD, ymd);
        values.put(COLUMN_TRAINING_TIME, time);
        values.put(COLUMN_CLUB_NUMBER, number);
        values.put(COLUMN_TRAINING_SYNC, sync);
        Log.d("ADD : " , seq);
        Log.d("ADD YMD : " , ymd);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_TRANNING_HEADER, null, values);
        db.close();
    }


    public void table2_addData(String tseq, String dseq, String time, String rweight, String lweight){
        ContentValues values=new ContentValues();
        values.put(COLUMN_TRAINING_SEQ, tseq);
        values.put(COLUMN_DETAIL_SEQ , dseq);
        values.put(COLUMN_REG_TIME, time);
        values.put(COLUMN_RIGHT_WEIGHT , rweight);
        values.put(COLUMN_LEFT_WEIGHT , lweight);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_TRANNING_DETAIL, null, values);
        db.close();
    }

    public void table3_addData(String seq, String fpath, String fname){
        ContentValues values=new ContentValues();
        values.put(COLUMN_TRAINING_SEQ, seq);
        values.put(COLUMN_FILE_PATH , fpath);
        values.put(COLUMN_FILE_NAME , fname);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_TRAINING_VIDEO_HIST , null, values);
        db.close();
    }

    public void table4_addData(String id, String name, String number){
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRO_ID , id);
        values.put(COLUMN_PRO_NAME , name);
        values.put(COLUMN_CLUB_NUMBER , number);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_PRO_DATA_HEADER , null, values);
        db.close();
    }

    public void table5_addData(String id, String seq, String rweight, String lweight){
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRO_ID, id);
        values.put(COLUMN_DETAIL_SEQ, seq);
        values.put(COLUMN_RIGHT_WEIGHT, rweight);
        values.put(COLUMN_LEFT_WEIGHT, lweight);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_PRO_DATA_DETAIL , null, values);
        db.close();
    }

    public void table6_addData(String dcode, String tcode){
        ContentValues values=new ContentValues();
        values.put(COLUMN_DIV_CODE , dcode);
        values.put(COLUMN_TASK_CODE , tcode);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_COMMON_CODE , null, values);
        db.close();
    }

    public void table7_addData(String id, String fpath, String fname){
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRO_ID, id);
        values.put(COLUMN_FILE_PATH, fpath);
        values.put(COLUMN_FILE_NAME, fname);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_PRO_VIDEO_HIST , null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        //db.execSQL("drop table if exists "+DATABASE_TABLE);
        onCreate(db);

    }

}