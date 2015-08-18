package com.jacky.myapplication.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacky on 8/18/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "myapplication.db";
    private static final int DB_VERSION = 1;

    private static DataBaseHelper mDBhelper;

    public DataBaseHelper (Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    /**
     * Single instance for DataBaseHelper.
     * @param context The invoker context
     * @return instance of DataBaseHelper
     */
    public static DataBaseHelper getInstance(Context context){
        if (mDBhelper == null){
            synchronized (DataBaseHelper.class){
                mDBhelper = new DataBaseHelper(context);
            }
        }
        return mDBhelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HISTORIES_MESSAGE_TABLE_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static final String HISTORIES_MESSAGE_TABLE_CREATE_SQL = " create table "
                    + ContentDescriptor.HistoriesMessage.NAME
                    + " ( "
                    + ContentDescriptor.HistoriesMessage.Cols.ID
                    + " integer primary key AUTOINCREMENT,"
                    + ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME
                    + " nvarchar(40),"
                    + ContentDescriptor.HistoriesMessage.Cols.HISTORY_MESSAGE_GROUP_TYPE
                    + " bigint)";
}
