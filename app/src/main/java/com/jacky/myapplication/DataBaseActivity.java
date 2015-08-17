package com.jacky.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jacky.myapplication.Util.DataBaseHelper;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBaseActivity extends Activity {
    private final static String LOG_TAG = "DataBaseActivity";

    private final static String DATA_NAME = "test.db";
    private final static String TABLE_NAME = "user";
    private final static String COLUMN_1 = "id";
    private final static String COLUMN_2 = "name";

    private Button mBtCreate;
    private Button mBtRead;
    private Button mBtInsert;
    private Button mBtDelete;
    private Button mBtUpdate;
    private Button mBtQuery;
    private TextView mTextViewShow;

    SQLiteDatabase database;

    private final static String[] names = {"Jack","Jone","Kate","Hurley","Sayid","Desmond","Jin","Ben"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        findViews();
        registerListeners();
    }

    private void registerListeners() {
        mBtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatabase();
            }
        });
        mBtRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDatabase();
            }
        });
        mBtInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });
        mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteItem();
            }
        });

        mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateItem();
            }
        });
        mBtQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //queryItem();
            }
        });
    }

    private void readDatabase() {
        if (database == null) {
            showToast("insert item failed due to no database created. Pls try create a new database");
            Log.e(LOG_TAG, "insertItem():insert item failed due to no database created. Pls try create a new database");
            return;
        }

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null, null);
        // if (cursor.moveToFirst()){
        String show = "";
        while (cursor.moveToNext()) {
            //  for (int i=0;i<cursor.getCount();i++){
           // cursor.move(i);
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_1));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_2));
            show += "id:\t" + String.valueOf(id) + "\t" + "name:\t" + name + "\n";
        }

            showToTextView(show);
      ////  }

    }

    private void showToTextView(String show) {
        if (show == null) return;
        mTextViewShow.setText(show);
    }

    private void insertItem() {
        if (database == null){
            showToast("insert item failed due to no database created. Pls try create a new database");
            Log.e(LOG_TAG,"insertItem():insert item failed due to no database created. Pls try create a new database");
            return;
        }

        if (database.isReadOnly()){
            showToast("insert item failed due to database is read only!");
            Log.e(LOG_TAG,"insertItem(): insert item failed due to database is read only!");
            return;
        }

        ContentValues cv = new ContentValues();

        //为user table添加第一列数据
        int random = new Random().nextInt(names.length);
        cv.put(COLUMN_1, new Integer(random));
        cv.put(COLUMN_2,names[random]);
        database.insert(TABLE_NAME, null, cv);
    }

    private void createDatabase() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,DATA_NAME,null,1);
        database = dataBaseHelper.getWritableDatabase();
    }

    private void findViews() {
        mBtCreate = (Button) findViewById(R.id.button_createDB);
        mBtRead = (Button) findViewById(R.id.button_readDB);
        mBtInsert = (Button) findViewById(R.id.button_insert);
        mBtDelete = (Button) findViewById(R.id.button_delete);
        mBtUpdate = (Button) findViewById(R.id.button_update);
        mBtQuery = (Button) findViewById(R.id.button_query);
        mTextViewShow = (TextView) findViewById(R.id.textView_showDB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
