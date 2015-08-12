package com.jacky.myapplication.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 28851620 on 8/12/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "DataBaseHelper";
    private static String DATA_NAME;
    private static int version = 1;
    private final static String COLUMN_1 = "id";
    private final static String COLUMN_2 = "name";



    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        DATA_NAME = name;
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG,"onCreate()");
        //创建数据可SQL语句,创建一个table（id:int,name:char）
        String sql = "create table user(id int,name varchar(20))";

        //执行创建数据库操作
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(LOG_TAG,"onUpgrade().");

    }
}
